package miniP.exception.board;

public class BoardSaveException extends RuntimeException{

    public BoardSaveException() {
        super();
    }

    public BoardSaveException(String message){
        super(message);
    }

    // 이 Throwable cause 예시가 궁금함 데이터를 어떻게 던저야 할 지.
    public BoardSaveException(String message, Throwable cause){
        super(message,cause);
    }
}
