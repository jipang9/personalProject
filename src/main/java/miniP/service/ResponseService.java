package miniP.service;

//import miniP.service.result.DataResult;
import miniP.service.result.Result;
import miniP.service.result.SingleResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ResponseService {

    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        setSuccessResult(result);
        result.setData(data);
        return result;
    }


    // 의미없는 데이터니까 setter을 열었다.
    public <T> Result getFailureResult(int code, String msg) {
        Result result = new Result();
        result.setStatusCode(HttpStatus.BAD_REQUEST.value());
        result.setMessage(msg);
        return result;
    }

    public Result getSuccessResult() {
        Result result = new Result();
        setSuccessResult(result);
        return result;
    }

    public void setSuccessResult(Result result) {
        result.setStatusCode(HttpStatus.OK.value());
        result.setMessage("성공");
    }

}
