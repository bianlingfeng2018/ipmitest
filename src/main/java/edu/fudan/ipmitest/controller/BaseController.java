package edu.fudan.ipmitest.controller;

import edu.fudan.ipmitest.ResponseBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public abstract class BaseController {

    /**
     * @return ResponseBase<T>
     */
    protected <T> ResponseBase<T> success() {
        return this.success(null);
    }

    /**
     * @param t
     * @param <T>
     * @return ResponseBase<T>
     */
    protected <T> ResponseBase<T> success(T t) {
        ResponseBase<T> responseBase = new ResponseBase<>();
        responseBase.setResult(t);
        return responseBase;
    }

    /**
     * @param errorCode
     * @return ResponseBase
     */
    protected <T> ResponseBase<T> error(String errorCode) {
        ResponseBase<T> response = new ResponseBase<>();
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setErrorCode(errorCode);
        return response;
    }
}
