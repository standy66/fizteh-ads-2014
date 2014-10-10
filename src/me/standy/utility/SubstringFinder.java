package me.standy.utility;

/** This interface denotes and abstract substring finder, which works in a stream mode.
 * Created by astepanov on 12.09.14.
 */
public interface SubstringFinder {
    /**
     * This method sets the current sample which classes implementing {@link me.standy.utility.SubstringFinder} search in text. <br>
     * Previous sample is removed, when new is set.
     * @param sample
     */
    public void setSample(String sample);

    /**
     * This method searches the sample in the current text.
     * @param c next character of the text
     * @return true if and only if the sample is a suffix of current text
     * @throws IllegalStateException
     */
    public boolean nextChar(char c) throws IllegalStateException;
}
