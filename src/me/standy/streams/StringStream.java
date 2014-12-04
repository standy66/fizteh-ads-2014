package me.standy.streams;

import java.util.NoSuchElementException;

/** {@link me.standy.streams.StringStream} is a wrapper around {@link java.lang.String}
 * that provides {@link me.standy.streams.CharStream} interface.
 * Created by astepanov on 10.10.14.
 */
public class StringStream implements CharStream {
    private int currentPosition;
    private final String string;

    /**
     * Constructs a new StringStream, initializing it with {@link java.lang.String} s.
     * @param s the string with which the @{link StringStream} will be initialized.
     */
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
