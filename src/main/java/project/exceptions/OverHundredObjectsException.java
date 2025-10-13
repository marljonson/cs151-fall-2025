package exceptions;

public class OverHundredObjectsException extends Exception {
    
    public OverHundredObjectsException() {
        System.out.println("Cannot create more than 100 objects of this class.");
    }

    public OverHundredObjectsException(String message) {
        super(message);
    }
}
 