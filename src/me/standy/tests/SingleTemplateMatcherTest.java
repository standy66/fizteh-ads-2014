package me.standy.tests;

import me.standy.MetaTemplateMatcher;
import me.standy.RandomCharStream;
import me.standy.SingleTemplateMatcher;
import me.standy.StringStream;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

/**
 * Created by astepanov on 10.10.14.
 */
public class SingleTemplateMatcherTest {
    private Collection<MetaTemplateMatcher.Occurrence> getRightAnswer(String template, int templateId, String text) {
        Collection<MetaTemplateMatcher.Occurrence> answer = new ArrayList<>();
        Pattern p = Pattern.compile(Pattern.quote(template));
        Matcher m = p.matcher(text);
        int start = 0;
        while(m.find(start)) {
            answer.add(new MetaTemplateMatcher.Occurrence(templateId, m.end() - 1));
            start = m.start() + 1;
        }
        return answer;
    }

    private class OccurrenceComparator implements Comparator<MetaTemplateMatcher.Occurrence> {
        @Override
        public int compare(MetaTemplateMatcher.Occurrence o1, MetaTemplateMatcher.Occurrence o2) {
            if (o1.getPosition() == o2.getPosition())
                return o1.getTemplateId() - o2.getTemplateId();
            return o1.getPosition() - o2.getPosition();
        }
    }

    private <T> void assertCollectionsIsomorphism(Collection<T> c1, Collection<T> c2) {
        MetaTemplateMatcher.Occurrence[] t1 = new MetaTemplateMatcher.Occurrence[c1.size()];
        MetaTemplateMatcher.Occurrence[] t2 = new MetaTemplateMatcher.Occurrence[c2.size()];
        c1.toArray(t1);
        c2.toArray(t2);
        Arrays.sort(t1, new OccurrenceComparator());
        Arrays.sort(t2, new OccurrenceComparator());
        assertArrayEquals(t1, t2);
    }

    private void testString(SingleTemplateMatcher matcher, String template, int templateId, String text) {
        Collection<MetaTemplateMatcher.Occurrence> rightAnswer = getRightAnswer(template, templateId, text);
        Collection<MetaTemplateMatcher.Occurrence> result = matcher.matchStream(new StringStream(text));
        assertCollectionsIsomorphism(rightAnswer, result);
    }

    private void stress(int streamSize, int templateSize, char[] alphabet) {
        RandomCharStream stream = new RandomCharStream(streamSize, alphabet);
        String template = new RandomCharStream(templateSize, alphabet).toString();
        SingleTemplateMatcher matcher = new SingleTemplateMatcher();
        int templateId = matcher.addTemplate(template);
        Collection<MetaTemplateMatcher.Occurrence> rightAnswer = getRightAnswer(template, templateId, stream.toString());
        Collection<MetaTemplateMatcher.Occurrence> result = matcher.matchStream(stream);
        assertCollectionsIsomorphism(rightAnswer, result);
    }

    @Test
    public void smallTest() {
        SingleTemplateMatcher matcher = new SingleTemplateMatcher();
        String template = "aba";
        int templateId = matcher.addTemplate(template);
        testString(matcher, template, templateId, "abacaba");
        testString(matcher, template, templateId, "ababababababa");
    }

    @Test
    public void largeTest() {
        stress(10000, 3, new char[] {'a', 'b'});
    }




    /*TODO:Add test class for them all

    public void testATemplate(String templre, CharStream text, MetaTemplateMatcher matcher) {
        testCase(SingleTemplateMatcher.class);
    }

    public void testCase(Class<MetaTemplateMatcher> a) {
        MetaTemplateMatcher x = a.newInstance();
        x.addTemplate("123");
        testATemplate("123", new StringStream("123"), x);
        testATemplate("123", new StringStream("123"), x);

        MetaTemplateMatcher p = a.newInstance();
        p.addTemplate("321");
        testATemplate("123", new StringStream("321"), x);
        testATemplate("123", new StringStream("321"), x);
    }
     */
}
