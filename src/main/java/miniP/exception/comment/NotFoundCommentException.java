package miniP.exception.comment;

public class NotFoundCommentException extends RuntimeException {

    public NotFoundCommentException() {
        super();
    }
    public NotFoundCommentException(String message){
        super(message);
    }
    public NotFoundCommentException(String message, Throwable cause){
        super(message,cause);
    }
}
