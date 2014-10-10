package me.standy.utility;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by astepanov on 12.09.14.
 */
public class DumbSubstringFinder implements SubstringFinder {
    private String sample = null;
    private Deque<Character> tail = new LinkedList<Character>();

    @Override
    public void setSample(String s) {
        if (sample != null)
            tail.clear();
        sample = s;
    }

    @Override
    public boolean nextChar(char c) throws IllegalStateException {
        if (sample == null)
            throw new IllegalStateException("Sample is null");
        tail.addLast(c);
        if (tail.size() > sample.length())
            tail.removeFirst();
        if (tail.size() == sample.length()) {
            Iterator<Character> it = tail.iterator();
            int i = 0;
            while (it.hasNext()) {
                if (sample.charAt(i) != it.next())
                    return false;
                i++;
            }
            return true;
        } else
            return false;
    }
}
