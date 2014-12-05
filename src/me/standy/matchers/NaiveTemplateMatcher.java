package me.standy.matchers;

import me.standy.streams.CharStream;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.max;

/**
 * {@link me.standy.matchers.NaiveTemplateMatcher} allows to test a stream against a bunch of templates.
 * The memory consumption is O(sum of templates' lengths)
 * Created by astepanov on 21.10.14.
 */
public class NaiveTemplateMatcher implements MetaTemplateMatcher {
    private final List<String> templates = new ArrayList<>();
    private int maxTemplateLength = 0;

    /**
     * Adds a string template to a set of templates. This class allows execution of addTemplate with the same argument many times.
     * In this case, new id is returned every time.
     *
     * @param template a {@link String} representation of the template to be added to this matcher.
     * @return i - 1, if addTemplate was successfully invoked i times.
     */
    @Override
    public int addTemplate(String template) throws IllegalArgumentException {
        if (template == null) {
            throw new IllegalArgumentException("Template should not be null");
        }
        templates.add(template);
        maxTemplateLength = max(template.length(), maxTemplateLength);
        return templates.size() - 1;
    }

    /**
     * Matches the set of templates that were added to this matcher against the stream.
     * Because this matcher is naive, the method's execution time is O(stream size * sum of templates' lengths)
     *
     * @param stream A {@link me.standy.streams.CharStream} that is going to be matched against the number of templates.
     */
    @Override
    public List<Occurrence> matchStream(CharStream stream) throws IllegalStateException, IllegalArgumentException {
        if (stream == null) {
            throw new IllegalArgumentException("stream is null");
        }
        if (templates.isEmpty()) {
            throw new IllegalStateException("Nothing to match against");
        }
        Deque<Character> buffer = new LinkedList<>();
        List<Occurrence> answer = new ArrayList<>();
        int pos = 0;
        while (!stream.isEmpty()) {
            buffer.addLast(stream.nextChar());
            if (buffer.size() > maxTemplateLength) {
                buffer.removeFirst();
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (Character character : buffer) {
                stringBuilder.append(character);
            }
            String cut = stringBuilder.toString();
            for (int i = 0; i < templates.size(); i++) {
                String template = templates.get(i);
                if (template.length() > cut.length())
                    continue;
                String subCut = cut.substring(cut.length() - template.length());
                if (template.equals(subCut)) {
                    answer.add(new Occurrence(i, pos));
                }
            }
            pos++;
        }
        return answer;
    }
}
