package me.standy.streams;

import java.util.NoSuchElementException;
import java.util.Random;

/** This class represents a stream of a random characters from the known alphabet.
 * Created by astepanov on 10.10.14.
 */
public class RandomCharStream implements CharStream {
    private Random generator;
    private int position;
    private final int streamSize;
    private final int alphabetSize;
    private final char[] alphabet;

    /**
     * Constructs a new {@link me.standy.streams.RandomCharStream}. {@link java.util.Random}'s seed initialized with streamSize
     * @param streamSize length of the stream.
     * @param alphabet the alphabet from which random characters are chosen.
     */
    public RandomCharStream(int streamSize, char[] alphabet) {
        this.alphabetSize = alphabet.length;
        this.alphabet = alphabet;
        this.streamSize = streamSize;
        generator = new Random(streamSize);
    }

    /**
     * Returns next random character,
     * @return random character from the alphabet.
     */
    @Override
    public char nextChar() throws NoSuchElementException {
        if (position < streamSize) {
            position++;
            return alphabet[generator.nextInt(alphabetSize)];
        } else {
            throw new NoSuchElementException("The stream has ended");
        }
    }

    /**
     *
     * @return true if and only if position >= streamSize
     */
    @Override
    public boolean isEmpty() {
        return position >= streamSize;
    }

    /**
     * Resets the stream to its' initial state.
     */
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
