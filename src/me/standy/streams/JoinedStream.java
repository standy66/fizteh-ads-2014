package me.standy.streams;

import java.util.NoSuchElementException;

/**
 * @author andrew
 *         Created by andrew on 17.12.14.
 */
public class JoinedStream implements CharStream {
    private CharStream[] streams;
    private int currentStream;

    public JoinedStream(CharStream... streams) {
        if (streams == null) {
            throw new IllegalArgumentException("streams is null");
        }
        currentStream = 0;
        this.streams = streams;
    }

    @Override
    public char nextChar() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return streams[currentStream].nextChar();
        }
    }

    @Override
    public boolean isEmpty() {
        while (currentStream < streams.length && streams[currentStream].isEmpty()) {
            currentStream++;
        }
        return currentStream >= streams.length;
    }
}
