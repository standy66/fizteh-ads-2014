package me.standy.matchers;

import java.util.List;

/**
 * Provides interface to classes that matches 2D char matrices with target matrix.
 * @author andrew
 *         Created by andrew on 19.12.14.
 */
public interface MetaCharMatrixMatcher {
    /**
     * Adds template matrix to this matcher.
     * @param template matrix to add.
     * @return the unique identifier of a template matrix, which will be used in {@link me.standy.matchers.Occurrence2D} class.
     * @throws UnsupportedOperationException if jagged matrix not supported or if multiple tempaltes not supported
     * @throws IllegalArgumentException if template is null
     */
    int addTemplate(String[] template) throws UnsupportedOperationException, IllegalStateException, IllegalArgumentException;

    /**
     * Tests a set of template matrices against target matrix.
     * @param target matrix to match against.
     * @return a list of {@link me.standy.matchers.Occurrence2D}.
     * @throws IllegalArgumentException if target is null
     * @throws IllegalStateException if no template was added.
     * @throws UnsupportedOperationException if jagged matrix not supported.
     */
    List<Occurrence2D> matchTarget(String[] target) throws IllegalArgumentException, IllegalStateException, UnsupportedOperationException;
}
