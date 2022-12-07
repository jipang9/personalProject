package miniP.exception;

public class BoardDeleteException extends RuntimeException{

    public BoardDeleteException() {
    }
    public BoardDeleteException(String message){
        super(message);
    }
    public BoardDeleteException(String message, Throwable cause){
        super(message,cause);
    }
}
