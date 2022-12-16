package miniP.exception.board;

public class BoardUpdateException extends RuntimeException{

    public BoardUpdateException() {
        super();
    }
    public BoardUpdateException(String message){
        super(message);
    }
    public BoardUpdateException(String message, Throwable cause){
        super(message,cause);
    }
}
