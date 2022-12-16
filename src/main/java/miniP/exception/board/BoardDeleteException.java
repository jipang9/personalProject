package miniP.exception.board;

public class BoardDeleteException extends RuntimeException{

    public BoardDeleteException() {
        super();
    }
    public BoardDeleteException(String message){
        super(message);
    }
    public BoardDeleteException(String message, Throwable cause){
        super(message,cause);
    }
}
