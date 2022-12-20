package miniP.exception.member;

public class MemberExistException extends RuntimeException {
    public MemberExistException(String msg) {
        super(msg);
    }
}
