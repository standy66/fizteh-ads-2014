package me.standy.matchers;

/** Occurrence2D class represents an occurrence of a template matrix in a target matrix
 * @author andrew
 *         Created by andrew on 19.12.14.
 */
public class Occurrence2D implements Comparable<Occurrence2D> {
    int x;
    int y;
    int templateId;

    /**
     * Constructor for MetaCharMatrixMatcher implementations.
     * @param x the x-position of the last col of occurrence
     * @param y the y-position of the last row of occurrence
     * @param templateId
     */
    public Occurrence2D(int x, int y, int templateId) {
        this.x = x;
        this.y = y;
        this.templateId = templateId;
    }

    /**
     * This method is used to acquire the x-coordinate of an occurrence, e.g. last col where matched template matrix ends.
     * @return the x-coordinate of the occurrence.
     */
    public int getX() {
        return x;
    }

    /**
     * This method is used to acquire the y-coordinate of an occurrence, e.g. last row where matched template matrix ends.
     * @return the y-coordinate of the occurrence.
     */
    public int getY() {
        return y;
    }

    /**
     * This method is used to acquire unique identifier of a template matrix matched, represented by this {@link me.standy.matchers.Occurrence2D} class
     * @return the unique identifier returned by {@link me.standy.matchers.MetaCharMatrixMatcher}'s addTemplate.
     */
    public int getTemplateId() {
        return templateId;
    }

    @Override
    public int hashCode() {
        return x ^ y ^ templateId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Occurrence2D) {
            Occurrence2D casted = (Occurrence2D) obj;
            return casted.x == x && casted.y == y && casted.templateId == templateId;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Occurrence2D o) {
        if (x == o.x) {
            if (y == o.y) {
                return templateId - o.templateId;
            }
            return y - o.y;
        }
        return x - o.x;
    }
}
