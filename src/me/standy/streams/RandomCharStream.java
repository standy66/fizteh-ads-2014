package me.standy.streams;

import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Created by astepanov on 10.10.14.
 */
public class RandomCharStream implements CharStream {
    private Random generator;
    private int position;
    private int streamSize;
    private int alphabetSize;
    private char[] alphabet;

    public RandomCharStream(int streamSize, char[] alphabet) throws IllegalArgumentException {
        if (alphabetSize > Character.MAX_VALUE)
            throw new IllegalArgumentException("Alphabet is too large");
        this.alphabetSize = alphabet.length;
        this.alphabet = alphabet;
        this.streamSize = streamSize;
        generator = new Random(streamSize);
    }

    @Override
    public char nextChar() throws NoSuchElementException {
        if (position < streamSize) {
            position++;
            return alphabet[generator.nextInt(alphabetSize)];
        } else {
            throw new NoSuchElementException("The stream has ended");
        }
    }

    @Override
    public boolean isEmpty() {
        return position >= streamSize;
    }

    public void reset() {
        generator = new Random(streamSize);
        position = 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int curPosition = position;
        Random curGenerator = generator;
        reset();
        while (!isEmpty()) {
            builder.append(nextChar());
        }
        position = curPosition;
        generator = curGenerator;
        return builder.toString();
    }
}
