package exceptions;

public class OverHundredObjects extends Exception {

    public OverHundredObjects() {
        System.out.println("Cannot create more than 100 objects of this class.");
    }
}
 