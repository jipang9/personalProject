package miniP.exception;

import lombok.RequiredArgsConstructor;
import miniP.common.ResponseService;
import miniP.common.result.Result;
import miniP.exception.board.BoardDeleteException;
import miniP.exception.board.BoardSaveException;
import miniP.exception.board.BoardUpdateException;
import miniP.exception.board.PostException;
import miniP.exception.comment.CommentUpdateException;
import miniP.exception.comment.FailPostComment;
import miniP.exception.comment.NotFoundCommentException;
import miniP.exception.member.CustomException;
import miniP.exception.member.IsNotWriterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(PostException.class) // 이 에러가 발생하면 여기로 옴.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result userPostException(){
        return responseService.getFailureResult(-101," 게시물 등록 실패");
    }

    @ExceptionHandler(BoardDeleteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result BoardDeleteException(){
        return responseService.getFailureResult(-102," 게시물 삭제오류 발생");}

    @ExceptionHandler(BoardUpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result BoardUpdateException(){return responseService.getFailureResult(-103," 게시물 수정오류 발생");}

    @ExceptionHandler(BoardSaveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result boardSaveFailException() {
        return responseService.getFailureResult(-106, "게시물 작성 실패");
    }




    @ExceptionHandler(IsNotWriterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result IsNotWriterException() {
        return responseService.getFailureResult(-403,  " 해당 게시물에 권한이 없습니다. ");
    }

    @ExceptionHandler(FailPostComment.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result FailPostComment() {
        return responseService.getFailureResult(-405, "댓글 작성 실패");
    }

    @ExceptionHandler(NotFoundCommentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result NotFoundCommentException() {
        return responseService.getFailureResult(-406, "해당 댓글이 존재하지 않습니다");
    }

    @ExceptionHandler(CommentUpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result CommentUpdateException() {
        return responseService.getFailureResult(-407, "댓글 수정 실패");
    }


    @ExceptionHandler({CustomException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity handleCustomException(CustomException ex) {
        return new ResponseEntity(new ErrorDto(ex.getExceptionStatus().getStatusCode(), ex.getExceptionStatus().getMessage()), HttpStatus.valueOf(ex.getExceptionStatus().getStatusCode()));
    }


}
