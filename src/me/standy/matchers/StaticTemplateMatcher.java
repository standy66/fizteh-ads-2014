package me.standy.matchers;

import me.standy.streams.CharStream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author andrew
 *         Created by andrew on 05.12.14.
 */
public class StaticTemplateMatcher implements MetaTemplateMatcher {
    private AhoNode rootNode = null;
    private final Set<String> templatesSet = new HashSet<>();
    private final List<String> templatesList = new ArrayList<>();

    @Override
    public int addTemplate(String template) throws UnsupportedOperationException, IllegalArgumentException {
        if (template == null) {
            throw new IllegalArgumentException("template should not be null");
        }
        if (rootNode != null) {
            throw new UnsupportedOperationException("All templates should be added before matchStream invoked");
        }
        if (templatesSet.contains(template)) {
            throw new UnsupportedOperationException("Template duplicates are not supported");
        }
        templatesSet.add(template);
        templatesList.add(template);
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
            rootNode = new AhoCorasickBuilder().build(templatesList);
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
                        result.add(new Occurrence(nextHard.stringId, position));
                    }
                    nextHard = nextHard.hardlink();
                }
            }
            position++;
        }
        return result;
    }
}
