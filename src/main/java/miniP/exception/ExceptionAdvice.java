package miniP.exception;

import lombok.RequiredArgsConstructor;
import miniP.exception.board.*;
import miniP.exception.comment.CommentUpdateException;
import miniP.exception.comment.FailPostComment;
import miniP.exception.comment.NotFoundCommentException;
import miniP.exception.login.LoginFailureException;
import miniP.exception.member.IsNotWriterException;
import miniP.exception.member.NotExistMemberException;
import miniP.exception.member.NotMemberSaveException;
import miniP.service.ResponseService;
import miniP.service.result.Result;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(NotExistMemberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result NotExistMemberException(){return responseService.getFailureResult(-104,"존재하지 않는 회원");}

    @ExceptionHandler(LoginFailureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result loginFailureException() {
        return responseService.getFailureResult(-105, "아이디 혹은 비밀번호가 틀립니다");
    }

    @ExceptionHandler(BoardSaveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result boardSaveFailException() {
        return responseService.getFailureResult(-106, "게시물 작성 실패");
    }

    @ExceptionHandler(NotExistTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result notExistTokenException() {
        return responseService.getFailureResult(-107, "토큰이 존재하지 않습니다.");
    }

    @ExceptionHandler(NotMemberSaveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result NotMemberSaveException() {
        return responseService.getFailureResult(-401, "이미 등록된 회원이 존재합니다");
    }

    @ExceptionHandler(NotFoundBoardException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result NotFoundBoardException() {
        return responseService.getFailureResult(-402,  " 해당 게시물이 존재하지 않습니다 ");
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


}
