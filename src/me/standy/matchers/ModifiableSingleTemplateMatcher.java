package me.standy.matchers;

import me.standy.streams.CharStream;
import me.standy.streams.JoinedStream;
import me.standy.streams.StringStream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author andrew
 *         Created by andrew on 17.12.14.
 */
public class ModifiableSingleTemplateMatcher extends SingleTemplateMatcher {
    protected Queue<Character> appended = new LinkedList<>();
    protected Queue<Character> prepended = new LinkedList<>();

    protected void recalculate() {
        String suffix = appended.stream().map(x -> x.toString()).collect(Collectors.joining());
        String prefix = prepended.stream().map(x -> x.toString()).collect(Collectors.joining());
        sample = String.join("", prefix, sample.substring(0, sample.length() - 1), suffix, String.valueOf((char) 0));
        samplePi = PiFunction.getPiFunction(sample);
        sampleLength = sample.length();
        appended.clear();
        prepended.clear();
    }

    public void appendCharToTemplate(char c) throws IllegalStateException {
        if (sample == null) {
            throw new IllegalStateException("no template was set");
        }
        appended.add(c);
        if (appended.size() + prepended.size() > sample.length()) {
            recalculate();
        }
    }

    public void prependCharToTemplate(char c) throws IllegalStateException {
        if (sample == null) {
            throw new IllegalStateException("no template was set");
        }
        prepended.add(c);
        if (appended.size() + prepended.size() > sample.length()) {
            recalculate();
        }
    }

    @Override
    public List<Occurrence> matchStream(CharStream stream) throws IllegalStateException, IllegalArgumentException {
        if (stream == null)
            throw new IllegalArgumentException("Stream should not be null");
        if (sample == null)
            throw new IllegalStateException("Method matchStream is called before any template was set.");
        int neededSize = prepended.size() + appended.size() + sample.length() - 1;
        Deque<Character> buffer = new LinkedList<>();
        while (buffer.size() < neededSize && !stream.isEmpty()) {
            buffer.add(stream.nextChar());
        }
        if (buffer.size() < neededSize) {
            return new ArrayList<>();
        } else {
            String read = buffer.stream().map(ch -> ch.toString()).collect(Collectors.joining());
            CharStream joined = new JoinedStream(new StringStream(read), stream);
            recalculate();
            return super.matchStream(joined);
        }
    }
}
