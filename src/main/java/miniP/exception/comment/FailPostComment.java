package miniP.exception.comment;

public class FailPostComment extends RuntimeException {

    public FailPostComment() {
        super();
    }
    public FailPostComment(String message){
        super(message);
    }
    public FailPostComment(String message, Throwable cause){
        super(message,cause);
    }
}
