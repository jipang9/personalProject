package miniP.exception;

public class PostException extends RuntimeException {
    public PostException() {
    }
    public PostException(String message){
        super(message);
    }
    public PostException(String message, Throwable cause){
        super(message,cause);
    }
}

