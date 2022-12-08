package miniP.exception;

public class NotExistMemberException extends RuntimeException {

    public NotExistMemberException() {
    }
    public NotExistMemberException(String message){
        super(message);
    }
    public NotExistMemberException(String message, Throwable cause){
        super(message,cause);
    }
}
