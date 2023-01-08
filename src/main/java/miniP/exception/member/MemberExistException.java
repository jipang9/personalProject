package miniP.exception.member;

public class MemberExistException extends RuntimeException {

    public MemberExistException() {
        super();
    }
    public MemberExistException(String message){
        super(message);
    }
    public MemberExistException(String message, Throwable cause){
        super(message,cause);
    }

}
