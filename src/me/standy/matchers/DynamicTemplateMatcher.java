package me.standy.matchers;

import me.standy.streams.CharStream;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.max;

/**
 * This class is similar to StaticTemplateMatcher, except addTemplate and matchStream invocations may alternate.
 * @author andrew
 *         Created by andrew on 18.12.14.
 */
public class DynamicTemplateMatcher implements MetaTemplateMatcher {
    int templateId = 0;
    boolean empty = true;
    List<AhoNode> forest = new ArrayList<>();
    List<String> templatesList = new ArrayList<>();
    private final Map<String, List<Integer>> listInverse = new HashMap<>();

    List<AhoNode> mergeForests(List<AhoNode> l1, List<AhoNode> l2) {
        List<AhoNode> answer = new ArrayList<>();
        AhoNode shift = null;
        AhoCorasickBuilder builder = new AhoCorasickBuilder();
        for (int pos = 0; pos < max(l1.size(), l2.size()) || shift != null; pos++) {
            if (pos >= l1.size()) {
                l1.add(null);
            }
            if (pos >= l2.size()) {
                l2.add(null);
            }
            AhoNode n1 = l1.get(pos);
            AhoNode n2 = l2.get(pos);
            int numbersOfNonNull = (n1 != null ? 1 : 0) + (n2 != null ? 1 : 0) + (shift != null ? 1 : 0);

            if (n1 == null) {
                if (n2 == null) {
                    if (shift == null) {
                        answer.add(null);
                    } else {
                        answer.add(shift);
                        shift = null;
                    }
                } else {
                    if (shift == null) {
                        answer.add(n2);
                    } else {
                        answer.add(null);
                        shift = builder.merge(shift, n2, templatesList);
                    }
                }
            } else {
                if (n2 == null) {
                    if (shift == null) {
                        answer.add(n1);
                    } else {
                        answer.add(null);
                        shift = builder.merge(shift, n1, templatesList);
                    }
                } else {
                    if (shift == null) {
                        answer.add(null);
                        shift = builder.merge(n1, n2, templatesList);
                    } else {
                        answer.add(builder.merge(n1, n2, templatesList));
                    }
                }
            }
        }
        return answer;
    }

    /**
     * This methods adds a template string to this matcher. Executes in amortized O(template length * log number of templates).
     * @throws IllegalArgumentException if a template is null
     */
    @Override
    public int addTemplate(String template) throws IllegalArgumentException {
        if (template == null) {
            throw new IllegalArgumentException("template should not be null");
        }
        AhoNode newNode = new AhoCorasickBuilder().build(Arrays.asList(new String[] {template}),
                Arrays.asList(new Integer[] {templateId}));
        templatesList.add(template);
        forest = mergeForests(forest, new ArrayList<>(Arrays.asList(new AhoNode[] {newNode})));
        if (listInverse.get(template) == null) {
            listInverse.put(template, new ArrayList<>());
        }
        listInverse.get(template).add(templateId);
        empty = false;
        return templateId++;
    }

    /**
     * Matches the set of templates that were added to this matcher against the stream. This method executes in O(stream size * log number of templates).
     * @throws IllegalStateException if no templates was added.
     * @throws IllegalArgumentException if stream is null.
     */
    @Override
    public List<Occurrence> matchStream(CharStream stream) throws IllegalStateException, IllegalArgumentException {
        if (stream == null) {
            throw new IllegalArgumentException("stream should not be null");
        }
        if (empty) {
            throw new IllegalStateException("no templates to match against");
        }

        HashSet<Occurrence> result = new HashSet<>();
        List<AhoNode> currentNodes = forest.stream().filter(node -> node != null).collect(Collectors.toCollection(ArrayList::new));


        int position = 0;
        while (!stream.isEmpty()) {
            char c = stream.nextChar();
            for (int i = 0; i < currentNodes.size(); i++) {
                AhoNode current = currentNodes.get(i).go(c);
                if (current.terminal || current.hardlink() != null) {
                    AhoNode nextHard = current;
                    while (nextHard != null) {
                        if (nextHard.terminal) {
                            String template = templatesList.get(nextHard.stringId);
                            for (Integer index : listInverse.get(template)) {
                                result.add(new Occurrence(index, position));
                            }
                        }
                        nextHard = nextHard.hardlink();
                    }
                }
                currentNodes.set(i, current);
            }

            position++;
        }

        return new ArrayList<>(result);
    }
}
