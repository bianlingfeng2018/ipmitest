package edu.fudan.ipmitest;

import edu.fudan.ipmitest.utils.HttpStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBase<T> {
    private int code;
    private String message;
    private Long timestamp;
    private String errorCode;
    private T result;

    public ResponseBase() {
        this.code = HttpStatus.OK;
    }

    public void setSucResponse(T result,String message){
        this.setCode(200);
        this.setMessage(message);
        this.setResult(result);
    }
    public void setUnSucResponse(T result,String message){
        this.setCode(500);
        this.setMessage(message);
        this.setResult(result);
    }
}
