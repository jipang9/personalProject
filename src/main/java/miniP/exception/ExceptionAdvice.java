package miniP.exception;

import lombok.RequiredArgsConstructor;
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

    @ExceptionHandler(PostException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result userPostException(){
        return responseService.getFailureResult(-101,"등록실패");
    }

    @ExceptionHandler(BoardDeleteException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result BoardDeleteException(){return responseService.getFailureResult(-102,"삭제오류 발생");}

    @ExceptionHandler(BoardUpdateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result BoardUpdateException(){return responseService.getFailureResult(-103,"수정오류 발생");}

    @ExceptionHandler(NotExistMemberException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result NotExistMemberException(){return responseService.getFailureResult(-104,"존재하지 않는 회원");}
}
