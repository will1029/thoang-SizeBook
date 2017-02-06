package thoang.thoang_sizebook;

/**
 * Created by Willi_000 on 2017-02-06.
 */

public class detailedPerson extends Person {
    public detailedPerson(String name) {
        super(name);
    }

    /**
     * Creates a string representing the person
     */
    @Override
    public String toString() {
        String message = this.getName() + ":\nDate: " + this.getDate();

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
