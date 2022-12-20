package miniP.exception.board;

public class NotFoundBoardException extends RuntimeException {

    public NotFoundBoardException() {
        super();
    }
    public NotFoundBoardException(String message){
        super(message);
    }
    public NotFoundBoardException(String message, Throwable cause){
        super(message,cause);
    }
}
