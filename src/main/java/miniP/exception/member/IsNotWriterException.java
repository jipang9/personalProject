package miniP.exception.member;

public class IsNotWriterException extends RuntimeException {

    public IsNotWriterException() {
        super();
    }
    public IsNotWriterException(String message){
        super(message);
    }
    public IsNotWriterException(String message, Throwable cause){
        super(message,cause);
    }
}
