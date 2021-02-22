package com.toyproj.pinchhitterhomerun.entity;

import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResponseResult<T> {
    private String result;
    private T response;
    private HttpStatus httpStatus;

    public ResponseResult(ServiceResult<T> serviceResult) {
        if (serviceResult.isSuccess()) {
            httpStatus = HttpStatus.OK;
            this.result = serviceResult.getResult();
            this.response = serviceResult.getResponse();
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
    }

    public ResponseResult(T response){
        this.response = response;
    }

    public ResponseResult<T> setResult(String result) {
        this.result = result;
        return this;
    }

    public ResponseResult<T> build() {
        final var responseResult = new ResponseResult<T>(this.response);
        responseResult.setResult(this.result);
        return responseResult;
    }
}
