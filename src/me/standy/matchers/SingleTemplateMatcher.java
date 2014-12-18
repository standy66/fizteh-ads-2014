package me.standy.matchers;

import me.standy.streams.CharStream;

import java.util.ArrayList;
import java.util.List;

/** This class allows to match a single template against {@link me.standy.streams.CharStream}
 * The memory consumption is O(template length)
 * Created by astepanov on 10.10.14.
 */
public class SingleTemplateMatcher implements MetaTemplateMatcher {
    protected String sample = null;
    protected int[] samplePi = null;
    protected int sampleLength;

    /**
     * This method returns a prefix function of a string s
     * @param s not null string
     * @return prefix function of s
     */
    protected static int[] getPiFunction(String s) {
        int length = s.length();
        int[] result = new int[length];
        result[0] = 0;
        for (int i = 1; i < length; ++i) {
            result[i] = nextPiValue(result, result[i - 1], s, s.charAt(i));
        }
        return result;
    }

    /**
     * This methods returns the next value of prefix function of string s, considering that previous values are
     * stored in array piFunction and the current pi function equals to currentPi
     * @param piFunction the previous values of prefix function
     * @param currentPi the current value of prefix function
     * @param s the string
     * @param nextChar the next character
     * @return the next value of prefix function
     */
    protected static int nextPiValue(int[] piFunction, int currentPi, String s, char nextChar) {
        while (nextChar != s.charAt(currentPi) && currentPi > 0)
            currentPi = piFunction[currentPi - 1];
        if (nextChar == s.charAt(currentPi))
            currentPi++;
        return currentPi;
    }

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
        samplePi = getPiFunction(sample);
        sampleLength = sample.length();
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
            currentPi = nextPiValue(samplePi, currentPi, sample, character);
            if (currentPi == sampleLength - 1) {
                answer.add(new Occurrence(0, currentPosition));
            }
            currentPosition++;
        }
        return answer;
    }
}
