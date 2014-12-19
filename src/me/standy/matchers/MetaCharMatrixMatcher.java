package me.standy.matchers;

import java.util.List;

/**
 * @author andrew
 *         Created by andrew on 19.12.14.
 */
public interface MetaCharMatrixMatcher {
    int addTemplate(String[] template) throws UnsupportedOperationException, IllegalArgumentException;

    List<Occurrence2D> matchTarget(String[] target) throws IllegalArgumentException, IllegalStateException, UnsupportedOperationException;
}
