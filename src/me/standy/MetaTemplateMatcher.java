package me.standy;

import java.util.Collection;

/**
 * Created by astepanov on 10.10.14.
 */
public interface MetaTemplateMatcher {
    public int addTemplate(String template) throws UnsupportedOperationException;

    public Collection<Occurrence> matchStream(CharStream stream);

    public class Occurrence {
        private int mTemplateId;
        private int mPosition;

        public Occurrence(int templateId, int position) {
            mTemplateId = templateId;
            mPosition = position;
        }

        public int getTemplateId() {
            return mTemplateId;
        }

        public int getPosition() {
            return mPosition;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Occurrence) {
                Occurrence casted = (Occurrence)obj;
                return casted.mPosition == mPosition && casted.mTemplateId == mTemplateId;
            } else {
                return false;
            }
        }
    }
}
