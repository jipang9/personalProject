package miniP.exception.member;

public class NotMemberSaveException extends RuntimeException {

    public NotMemberSaveException(){
        super();
    }
    public NotMemberSaveException(String msg) {
        super(msg);
    }
}
