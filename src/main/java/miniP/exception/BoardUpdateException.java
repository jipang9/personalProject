package miniP.exception;

public class BoardUpdateException extends RuntimeException{

    public BoardUpdateException() {
    }
    public BoardUpdateException(String message){
        super(message);
    }
    public BoardUpdateException(String message, Throwable cause){
        super(message,cause);
    }
}
