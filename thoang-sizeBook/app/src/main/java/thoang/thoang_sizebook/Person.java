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
     *      Default for date is current date
     *
     * @param name The name of the person in text (string) format
     */
    public Person(String name) {
        this.name = name;
        this.date = new Date();
        this.neck = -1.0;
        this.bust = -1.0;
        this.chest = -1.0;
        this.waist = -1.0;
        this.hip = -1.0;
        this.inseam = -1.0;
        this.comment = "Not comment available.";
    }
    /**
     * Creates a string representing the person
     */
    @Override
    public String toString() {
        return(this.name + ": " + this.bust + ", " + this.chest +
                ", " + this.waist +  ", " + this.inseam);
    }

    /**
     * Gets the person's name
     * @return a string representing the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the date, which lets the user know when the dimensions are valid
     * @return a date with format of yyyy-mm-dd
     */
    public Date getDate() {
        return date;
    }

    /**
     * Get the neck circumference, in inches
     * @return a number of type double representing the neck circumference
     */
    public double getNeck() {
        return neck;
    }

    /**
     * Get the bust circumference, in inches
     * @return a number of type double representing the bust circumference
     */
    public double getBust() {
        return bust;
    }

    /**
     * Get the chest circumference, in inches
     * @return a number of type double representing the chest circumference
     */
    public double getChest() {
        return chest;
    }

    /**
     * Get the waist circumference, in inches
     * @return a number of type double representing the waist circumference
     */
    public double getWaist() {
        return waist;
    }

    /**
     * Get the hip circumference, in inches
     * @return a number of type double representing the hip circumference
     */
    public double getHip() {
        return hip;
    }

    /**
     * Gets the inseam length in inches
     * @return a double type representing the inseam length
     */
    public double getInseam() {
        return inseam;
    }

    /**
     * Gets the text comment
     * @return a text comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set/change name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets date in yyyy-mm-dd format
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Set neck circumference
     * @param neck
     */
    public void setNeck(double neck) {
        this.neck = neck;
    }

    /**
     * Set bust circumference
     * @param bust
     */
    public void setBust(double bust) {
        this.bust = bust;
    }

    /**
     * Set chest circumference
     * @param chest
     */
    public void setChest(double chest) {
        this.chest = chest;
    }

    /**
     * Set waist circumference
     * @param waist
     */
    public void setWaist(double waist) {
        this.waist = waist;
    }

    /**
     * Set hip circumference
     * @param hip
     */
    public void setHip(double hip) {
        this.hip = hip;
    }

    /**
     * Set inseam circumference
     * @param inseam
     */
    public void setInseam(double inseam) {
        this.inseam = inseam;
    }

    /**
     * Sets the text comment
     * @param comment a text based comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
