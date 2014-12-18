package me.standy.matchers;

import me.standy.streams.CharStream;

import java.util.*;

/**
 * @author andrew
 *         Created by andrew on 05.12.14.
 */
public class StaticTemplateMatcher implements MetaTemplateMatcher {
    private AhoNode rootNode = null;
    private final List<String> templatesList = new ArrayList<>();
    private final Map<String, List<Integer>> listInverse = new HashMap<>();

    @Override
    public int addTemplate(String template) throws UnsupportedOperationException, IllegalArgumentException {
        if (template == null) {
            throw new IllegalArgumentException("template should not be null");
        }
        if (rootNode != null) {
            throw new UnsupportedOperationException("All templates should be added before matchStream invoked");
        }

        templatesList.add(template);
        if (listInverse.get(template) == null) {
            listInverse.put(template, new ArrayList<>());
        }
        listInverse.get(template).add(templatesList.size() - 1);
        return templatesList.size() - 1;
    }

    @Override
    public List<Occurrence> matchStream(CharStream stream) throws IllegalStateException, IllegalArgumentException {
        if (stream == null) {
            throw new IllegalArgumentException("stream should not be null");
        }
        if (templatesList.isEmpty()) {
            throw new IllegalStateException("no templates to match against");
        }
        if (rootNode == null) {
            Integer[] identity = new Integer[templatesList.size()];
            for (int i = 0; i < identity.length; i++) {
                identity[i] = i;
            }
            rootNode = new AhoCorasickBuilder().build(templatesList, Arrays.asList(identity));
        }
        AhoNode current = rootNode;
        List<Occurrence> result = new ArrayList<>();
        int position = 0;
        while (!stream.isEmpty()) {
            char c = stream.nextChar();
            current = current.go(c);
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
            position++;
        }
        return result;
    }
}
