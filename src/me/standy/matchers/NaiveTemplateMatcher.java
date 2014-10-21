package me.standy.matchers;

import me.standy.matchers.utility.Occurrence;
import me.standy.streams.CharStream;

import java.util.Collection;

/**
 * Created by astepanov on 21.10.14.
 */
public class NaiveTemplateMatcher implements MetaTemplateMatcher {

    @Override
    public int addTemplate(String template) {
        return 0;
    }

    @Override
    public Collection<Occurrence> matchStream(CharStream stream) {
        return null;
    }
}
