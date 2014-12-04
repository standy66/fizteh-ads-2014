package me.standy.matchers;

import me.standy.streams.CharStream;

import java.util.List;

/**
 * Created by astepanov on 21.10.14.
 */
public class NaiveTemplateMatcher implements MetaTemplateMatcher {

    @Override
    public int addTemplate(String template) {
        return 0;
    }

    @Override
    public List<Occurrence> matchStream(CharStream stream) {
        return null;
    }
}
