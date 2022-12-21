package miniP.exception.comment;

public class CommentUpdateException extends RuntimeException {

    public CommentUpdateException() {
        super();
    }
    public CommentUpdateException(String message){
        super(message);
    }
    public CommentUpdateException(String message, Throwable cause){
        super(message,cause);
    }
}
