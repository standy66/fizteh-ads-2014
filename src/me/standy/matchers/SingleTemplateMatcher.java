package me.standy.matchers;

import me.standy.streams.CharStream;

import java.util.ArrayList;
import java.util.List;

/** This class allows one to match a single template against {@link me.standy.streams.CharStream}
 * The memory consumption is O(template length)
 * Created by astepanov on 10.10.14.
 */
public class SingleTemplateMatcher implements MetaTemplateMatcher {

    private String sample = null;
    private int[] samplePi = null;
    private int templateLength;

    /**
     * This method is used to set a string as a template to the matcher. Note that if the
     * method was called twice with non-null arguments, the second invocation will cause.
     * This method executes in O(template length) time
     * {@link java.lang.UnsupportedOperationException}
     * @param template a {@link String} representation of the template to be added to this matcher.
     * @return 0
     * @throws UnsupportedOperationException if and only if the method was called multiple times.
     */
    @Override
    public int addTemplate(String template) throws UnsupportedOperationException, IllegalArgumentException {
        if (template == null)
            throw new IllegalArgumentException("Template should not be null");
        if (sample != null)
            throw new UnsupportedOperationException("This class doesn't support multiple templates.");
        sample = template + (char) 0;
        templateLength = sample.length();
        samplePi = new int[templateLength];
        samplePi[0] = 0;
        for (int i = 1; i < templateLength; ++i) {
            int pi = samplePi[i - 1];
            while (sample.charAt(i) != sample.charAt(pi) && pi > 0)
                pi = samplePi[pi - 1];
            if (sample.charAt(i) == sample.charAt(pi))
                pi++;
            samplePi[i] = pi;
        }
        return 0;
    }

    /**
     * Matches the template that was added to this matcher with the stream. This method executes in O(stream length) time.
     * @param stream A {@link me.standy.streams.CharStream} that is going to be matched against the template.
     * @return A {@link java.util.List} of {@link me.standy.matchers.Occurrence} with templateId == 0 and positions equal to position
     * of the last character of occurrence in the stream.
     * @throws IllegalStateException if the matchStream was invoked before any template was set.
     */
    @Override
    public List<Occurrence> matchStream(CharStream stream) throws IllegalStateException, IllegalArgumentException {
        if (stream == null)
            throw new IllegalArgumentException("Stream should not be null");
        if (sample == null)
            throw new IllegalStateException("Method matchStream is called before any template was set.");
        List<Occurrence> answer = new ArrayList<>();
        int currentPosition = 0;
        int currentPi = 0;
        while (!stream.isEmpty()) {
            char character = stream.nextChar();
            while (character != sample.charAt(currentPi) && currentPi > 0)
                currentPi = samplePi[currentPi - 1];
            if (character == sample.charAt(currentPi))
                currentPi++;
            if (currentPi == templateLength - 1) {
                answer.add(new Occurrence(0, currentPosition));
            }
            currentPosition++;
        }
        return answer;
    }
}
