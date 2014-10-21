package me.standy.streams;

import java.util.NoSuchElementException;

/**
 * Created by astepanov on 10.10.14.
 */
public class StringStream implements CharStream {

    private int currentPosition;
    private String string;

    public StringStream(String s) {
        string = s;
        currentPosition = 0;
    }

    @Override
    public char nextChar() throws NoSuchElementException {
        if (currentPosition < string.length()) {
            return string.charAt(currentPosition++);
        } else {
            throw new NoSuchElementException("String has ended");
        }
    }

    @Override
    public boolean isEmpty() {
        return currentPosition >= string.length();
    }
}
