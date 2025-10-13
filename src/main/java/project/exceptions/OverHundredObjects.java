package project.exceptions;

public class OverHundredObjects extends Exception {

    public OverHundredObjects() {
        super();
        //System.out.println("Cannot create more than 100 objects of this class."); //will use this error when we catch this, not here
    }

    public OverHundredObjects(String message){
        super(message);
    }

    public OverHundredObjects(String message, Throwable cause){
        super(message, cause);
    }

    public OverHundredObjects(Throwable cause){
        super(cause);
    }
}