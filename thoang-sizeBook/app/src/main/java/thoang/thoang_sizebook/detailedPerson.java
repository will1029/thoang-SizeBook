package thoang.thoang_sizebook;

/**
 * This class inherits from Person. It expands the toString method to
 *  provide a more detailed description of the person.
 *
 *  @author thoang
 *  @see Person
 */

public class DetailedPerson extends Person {

    /**
     * Constructor
     *
     * @param name The name of the person
     * @see Person
     */
    public DetailedPerson(String name) {
        super(name);
    }

    /**
     * Creates a string representing the person
     */
    @Override
    public String toString() {
        String message = "Name: " + this.getName() +
                ":\nDate: " + this.getDate();

        if(this.getNeck() == -1.0) {
            message = message + "\nNeck: Not Avaliable";
        } else {
            message = message + "\nNeck: " + this.getNeck();
        }

        if(this.getBust() == -1.0) {
            message = message + "\nBust: Not Avaliable";
        } else {
            message = message + "\nBust: " + this.getBust();
        }

        if(this.getChest() == -1.0) {
            message = message + "\nChest: Not Avaliable";
        } else {
            message = message + "\nChest: " + this.getChest();
        }

        if(this.getWaist() == -1.0) {
            message = message + "\nWaist: Not Avaliable";
        } else {
            message = message + "\nWaist: " + this.getWaist();
        }

        if(this.getHip() == -1.0) {
            message = message + "\nHip: Not Avaliable";
        }else {
            message = message + "\nHip: " + this.getHip();
        }

        if(this.getInseam() == -1.0) {
            message = message + "\nInseam: Not Avaliable";
        } else {
            message = message + "\nInseam: " + this.getInseam();
        }

        message = message + "\nComment: " + this.getComment();

        return(message);
    }

}
