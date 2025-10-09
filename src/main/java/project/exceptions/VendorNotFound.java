package project.exceptions;

public class VendorNotFound extends Exception{
    public VendorNotFound(){
        super();
    }

    public VendorNotFound(String message){
        super(message);
    }

    public VendorNotFound(String message, Throwable cause){
        super(message, cause);
    }

    public VendorNotFound(Throwable cause){
        super(cause);
    }

}
