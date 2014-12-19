package me.standy.matchers;

import me.standy.streams.CharStream;

import java.util.*;

/**
 * This class represents matcher based on Aho Corasick algorithm. Note that all templates should be added before
 * matchStream invoked.
 * @author andrew
 *         Created by andrew on 05.12.14.
 */
public class StaticTemplateMatcher implements MetaTemplateMatcher {
    private AhoNode rootNode = null;
    private final List<String> templatesList = new ArrayList<>();
    private final Map<String, List<Integer>> listInverse = new HashMap<>();

    /**
     * Adds a template to this matcher. This method's running time os O(template length)
     * @param template a {@link String} representation of the template to be added to this matcher.
     * @return an id of a template used in {@link me.standy.matchers.Occurrence} class
     * @throws UnsupportedOperationException if a template was added after matchStream invoked.
     * @throws IllegalArgumentException if the template string is null
     */
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

    /**
     * Matches the set of templates that were added to this matcher against the stream. This method's running time
     * is O(stream size + total number of occurrences).
     * @param stream A {@link me.standy.streams.CharStream} that is going to be matched against the number of templates
     * @throws IllegalStateException if no templates was added to this matcher
     * @throws IllegalArgumentException if stream is null
     */
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
