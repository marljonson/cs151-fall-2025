package exceptions;

public class MatchaOverflowException extends Exception {

    public MatchaOverflowException() {
        System.out.println("Cup cannot fit any more ice cubes.");
    }

    public MatchaOverflowException(String message) {
        super(message);
    }
}
 