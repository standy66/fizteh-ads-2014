package me.standy;

import me.standy.utility.KMPSubstringFinder;
import me.standy.utility.SubstringFinder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by astepanov on 10.10.14.
 */
public class SingleTemplateMatcher implements MetaTemplateMatcher {
    private SubstringFinder finder = null;

    @Override
    public int addTemplate(String template) throws UnsupportedOperationException {
        if (finder != null) {
            throw new UnsupportedOperationException("This class doesn't support multiple templates");
        }
        finder = new KMPSubstringFinder();
        finder.setSample(template);
        return 0;
    }

    @Override
    public Collection<Occurrence> matchStream(CharStream stream) {
        Collection<Occurrence> answer = new ArrayList<>();
        int currentPosition = 0;
        while (!stream.isEmpty()) {
            char c = stream.nextChar();
            if (finder.nextChar(c)) {
                answer.add(new Occurrence(0, currentPosition));
            }
            currentPosition++;
        }
        return answer;
    }
}
