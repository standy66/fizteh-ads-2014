package me.standy.matchers;

import me.standy.streams.CharStream;

import java.util.List;

/** This interface encapsulates basic string template-matching class.
 * Created by astepanov on 10.10.14.
 */
public interface MetaTemplateMatcher {

    /**
     * Adds the template to this matcher.
     * @param template a {@link String} representation of the template to be added to this matcher.
     * @return id of the template that will be used in {@link me.standy.matchers.Occurrence}
     * @throws UnsupportedOperationException if multiple templates are not supported or if this method was called after matchStream
     * @throws java.lang.IllegalArgumentException if template is null
     */
    public int addTemplate(String template) throws UnsupportedOperationException, IllegalArgumentException;

    /**
     * Matches the set of templates that were added to this matcher with the stream.
     * @param stream A {@link me.standy.streams.CharStream} that is going to be matched against the number of templates.
     * @return A {@link java.util.List} of {@link me.standy.matchers.Occurrence} representing the outcome of the matching process.
     * The templateId of each Occurrence corresponds to the one that addTemplate returned when that template was added.
     * The position of each Occurrence corresponds to the index of last character of the occurrence in the stream.
     * @throws IllegalStateException if no template was added
     * @throws java.lang.IllegalArgumentException if stream is null
     */
    public List<Occurrence> matchStream(CharStream stream) throws IllegalStateException, IllegalArgumentException;

}
