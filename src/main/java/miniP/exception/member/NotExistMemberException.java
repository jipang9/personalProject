package miniP.exception.member;

public class NotExistMemberException extends RuntimeException {

    public NotExistMemberException() {
        super();
    }
    public NotExistMemberException(String message){
        super(message);
    }
    public NotExistMemberException(String message, Throwable cause){
        super(message,cause);
    }
}
