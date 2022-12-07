package miniP.service;

import miniP.service.result.Result;
import miniP.service.result.SingleResult;
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

    public void setSuccessResult(Result result) {
        result.setSuccess(true);
        result.setCode(0);
        result.setMsg("성공");
    }

    public Result getFailureResult(int code, String msg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
