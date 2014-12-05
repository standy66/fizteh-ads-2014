package me.standy.matchers;


/** Occurrence class represents occurrence of the template string in the stream.
 * Created by astepanov on 21.10.14.
 */
public class Occurrence implements Comparable<Occurrence> {
    private int templateId;
    private int position;

    /**
     * Constructor for implementations of MetaTemplateMatcher.
     * @param templateId identifier of the template that matched the stream in the position.
     * @param position the position in which the template matched the stream.
     */
    public Occurrence(int templateId, int position) {
        this.templateId = templateId;
        this.position = position;
    }

    /**
     * This method is used to acquire templateId.
     * @return identifier of the template that matched the stream in the position
     */
    public int getTemplateId() {
        return templateId;
    }

    /**
     * This method is used to acquire position
     * @return the position in which the template matched the stream
     */
    public int getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Occurrence) {
            Occurrence casted = (Occurrence) obj;
            return casted.position == position && casted.templateId == templateId;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return templateId ^ position;
    }

    @Override
    public String toString() {
        return String.format("%s[tId: %d pos: %d]", getClass().getSimpleName(), templateId, position);
    }

    @Override
    public int compareTo(Occurrence o) {
        if (position == o.position)
            return templateId - o.templateId;
        return position - o.position;
    }
}
