package me.standy.streams;

import java.util.NoSuchElementException;

/**
 * This interface denotes an abstract stream of characters.
 * Created by astepanov on 10.10.14.
 */
public interface CharStream {
    /**
     * This method is used to acquire the next symbol of the stream.
     * @return next character in the stream.
     * @throws NoSuchElementException if and only if the stream is empty (i.e. isEmpty() returns true)
     */
    char nextChar() throws NoSuchElementException;

    /**
     * This method is used to test if the stream is empty.
     * @return true if and only if the stream is empty.
     */
    boolean isEmpty();
}
