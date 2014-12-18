package me.standy.matchers;

import me.standy.streams.CharStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author andrew
 *         Created by andrew on 18.12.14.
 */
public class WildcardSingleTemplateMatcher extends SingleTemplateMatcher {
    int[][] piFunctions = null;
    String[] samples = null;

    @Override
    public int addTemplate(String template) throws UnsupportedOperationException, IllegalArgumentException {
        if (template == null)
            throw new IllegalArgumentException("Template should not be null");
        if (samples != null)
            throw new UnsupportedOperationException("This class doesn't support multiple templates.");
        samples = Arrays.asList(template.split(Pattern.quote("?"), -1)).stream().map(s -> s + (char) 0).toArray((size) -> new String[size]);
        piFunctions = new int[samples.length][];
        for (int i = 0; i < samples.length; i++) {
            piFunctions[i] = getPiFunction(samples[i]);
        }
        return 0;
    }

    @Override
    public List<Occurrence> matchStream(CharStream stream) throws IllegalStateException, IllegalArgumentException {
        if (stream == null)
            throw new IllegalArgumentException("Stream should not be null");
        if (samples == null)
            throw new IllegalStateException("Method matchStream is called before any template was set.");
        List<Occurrence> answer = new ArrayList<>();
        ArrayList<Integer>[] sufficesSizes = new ArrayList[samples.length];
        for (int i = 0; i < sufficesSizes.length; i++) {
            sufficesSizes[i] = new ArrayList<>();
            sufficesSizes[i].add(0);
        }
        for (int currentPosition = 0; !stream.isEmpty(); currentPosition++) {
            char nextChar = stream.nextChar();
            for (int suffixNumber = 0; suffixNumber < sufficesSizes.length; suffixNumber++) {
                int len = sufficesSizes[suffixNumber].size();
                int currentPi = sufficesSizes[suffixNumber].get(len - 1);
                sufficesSizes[suffixNumber].add(nextPiValue(piFunctions[suffixNumber], currentPi, samples[suffixNumber], nextChar));
            }
            int sufIndex = sufficesSizes.length - 1;
            int sufPosition = currentPosition + 1;
            while (sufIndex >= 0 && sufPosition >= 0 && sufficesSizes[sufIndex].get(sufPosition) == samples[sufIndex].length() - 1) {
                sufPosition -= samples[sufIndex].length();
                sufIndex--;
            }
            if (sufIndex < 0) {
                sufPosition++;
            }
            if (sufIndex < 0 && sufPosition >= 0) {
                answer.add(new Occurrence(0, currentPosition));
            }
        }
        return answer;
    }
}
