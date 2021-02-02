package com.toyproj.pinchhitterhomerun.entity;

import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
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
}
