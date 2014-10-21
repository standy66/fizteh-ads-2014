package me.standy.tests;

import me.standy.matchers.utility.Occurrence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by astepanov on 21.10.14.
 */
public class Utility {
    public static <T extends Comparable<? super T>> boolean isListsIsomorphic(List<T> a, List<T> b) {
        Collections.sort(a);
        Collections.sort(b);
        return a.equals(b);
    }

    public static List<Occurrence> getListOfOccurrences(String[] templates, int[] templateIds, String text) {
        List<Occurrence> answer = new ArrayList<>();
        assert templates.length == templateIds.length;

        for (int i = 0; i < templates.length; i++) {
            String template = templates[i];
            int templateId = templateIds[i];

            Pattern p = Pattern.compile(Pattern.quote(template));
            Matcher m = p.matcher(text);
            int start = 0;
            while (start <= text.length() && m.find(start)) {
                if (m.end() > 0)
                    answer.add(new Occurrence(templateId, m.end() - 1));
                start = m.start() + 1;
            }
        }
        return answer;
    }
}
