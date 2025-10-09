package project.exceptions;

public class ProductNotFound extends Exception{

    public ProductNotFound(){
        super();
    }

    public ProductNotFound(String message){
        super(message);
    }

    public ProductNotFound(String message, Throwable cause){
        super(message, cause);
    }

    public ProductNotFound(Throwable cause){
        super(cause);
    }

}
