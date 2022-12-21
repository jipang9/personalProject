package miniP.exception.comment;

public class CommentDeleteException extends RuntimeException {

    public CommentDeleteException() {
        super();
    }
    public CommentDeleteException(String message){
        super(message);
    }
    public CommentDeleteException(String message, Throwable cause){
        super(message,cause);
    }
}
