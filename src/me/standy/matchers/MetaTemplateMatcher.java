package me.standy.matchers;

import me.standy.matchers.utility.Occurrence;
import me.standy.streams.CharStream;

import java.util.List;

/**
 * Created by astepanov on 10.10.14.
 */
public interface MetaTemplateMatcher {
    public int addTemplate(String template) throws UnsupportedOperationException;

    public List<Occurrence> matchStream(CharStream stream) throws IllegalStateException;

}
