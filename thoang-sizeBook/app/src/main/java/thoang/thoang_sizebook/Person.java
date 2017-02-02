package thoang.thoang_sizebook;

import java.util.Date;

/**
 * Represents a person. Keeps track of all data about a person
 *
 * @author thoang
 * @version 1.0
 */

public class Person {
    private String name;
    private Date date;
    private double neck;
    private double bust;
    private double chest;
    private double waist;
    private double hip;
    private double inseam;
    private String comment;

    /**
     * Constructor.
     * Everything is defaulted to not available (except name)
     *      -1.0 for numerical
     *      No comment available for text
     *
     * @param name The name of the person in text (string) format
     */
    public Person(String name) {
        this.name = name;
        this.neck = -1.0;
        this.bust = -1.0;
        this.chest = -1.0;
        this.waist = -1.0;
        this.hip = -1.0;
        this.inseam = -1.0;
        this.comment = "Not comment available.";
    }

    /**
     * Gets the text comment
     *
     * @return a text comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the text comment
     *
     * @param comment a text based comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
