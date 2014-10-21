package me.standy.matchers;

import me.standy.matchers.utility.Occurrence;
import me.standy.streams.CharStream;

import java.util.Collection;

/**
 * Created by astepanov on 10.10.14.
 */
public interface MetaTemplateMatcher {
    public int addTemplate(String template);

    public Collection<Occurrence> matchStream(CharStream stream);

}
