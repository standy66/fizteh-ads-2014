package me.standy;

import java.util.NoSuchElementException;

/**
 * Created by astepanov on 10.10.14.
 */
public interface CharStream {
    char nextChar() throws NoSuchElementException;
    boolean isEmpty();
}
