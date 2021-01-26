package com.toyproj.pinchhitterhomerun.entity;

import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResult<T> {
    private String result;
    private T response;

    public ServiceResult(ErrorMessage result) {
        this(result, null);
    }

    public ServiceResult(ErrorMessage result, T response) {
        this.result = result.getMessage();
        this.response = response;
    }

    public boolean isSuccess() {
        return response != null;
    }
}
