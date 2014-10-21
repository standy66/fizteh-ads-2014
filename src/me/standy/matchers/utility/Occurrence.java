package me.standy.matchers.utility;

/**
* Created by astepanov on 21.10.14.
*/
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
