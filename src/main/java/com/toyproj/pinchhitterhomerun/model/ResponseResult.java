package com.toyproj.pinchhitterhomerun.model;

import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseResult<T> {
    private ErrorMessage result;
    private T response;

    public ResponseResult(ErrorMessage result) {
        this(result, null);
    }

    public ResponseResult(ErrorMessage result, T response) {
        this.result = result;
        this.response = response;
    }
}
