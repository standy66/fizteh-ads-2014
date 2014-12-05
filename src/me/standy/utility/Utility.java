package me.standy.utility;

import me.standy.matchers.Occurrence;

import java.lang.reflect.Array;
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

    public static <T> T[] merge(T[] array1, T[] array2) {
        T[] result = (T[]) Array.newInstance(array1.getClass().getComponentType(), array1.length + array2.length);
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    public static <T> List<T[]> cartesianProduct(T[][] x, T[][] y) {
        List<T[]> answer = new ArrayList<>();

        for (T[] o1 : x) {
            for (T[] o2 : y) {
                answer.add(merge(o1, o2));
            }
        }
        return answer;
    }
}
