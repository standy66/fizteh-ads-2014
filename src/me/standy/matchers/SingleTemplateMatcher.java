package me.standy.matchers;

import me.standy.matchers.utility.Occurrence;
import me.standy.streams.CharStream;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by astepanov on 10.10.14.
 */
public class SingleTemplateMatcher implements MetaTemplateMatcher {

    private String sample = null;
    private int[] samplePi = null;
    private int templateLength;

    @Override
    public int addTemplate(String template) throws UnsupportedOperationException {
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

    @Override
    public List<Occurrence> matchStream(CharStream stream) throws IllegalStateException {
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
