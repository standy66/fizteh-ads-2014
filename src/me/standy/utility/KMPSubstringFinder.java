package me.standy.utility;

/**
 * Created by astepanov on 12.09.14.
 */
public class KMPSubstringFinder implements SubstringFinder {
    private String sample = null;
    private int[] samplePi = null;
    private int n;
    private int currentPi = 0;

    @Override
    public void setSample(String s) {
        if (sample != null)
            currentPi = 0;
        sample = s + (char) 0;
        n = sample.length();
        samplePi = new int[n];
        samplePi[0] = 0;
        for (int i = 1; i < n; ++i) {
            int pi = samplePi[i - 1];
            while (sample.charAt(i) != sample.charAt(pi) && pi > 0)
                pi = samplePi[pi - 1];
            if (sample.charAt(i) == sample.charAt(pi))
                pi++;
            samplePi[i] = pi;
        }
    }

    @Override
    public boolean nextChar(char c) throws IllegalStateException {
        if (sample == null)
            throw new IllegalStateException("Sample is null");
        while (c != sample.charAt(currentPi) && currentPi > 0)
            currentPi = samplePi[currentPi - 1];
        if (c == sample.charAt(currentPi))
            currentPi++;
        if (currentPi == n - 1) {
            return true;

        } else
            return false;
    }
}
