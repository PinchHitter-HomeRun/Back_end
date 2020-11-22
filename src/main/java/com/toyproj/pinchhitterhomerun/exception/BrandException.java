package com.toyproj.pinchhitterhomerun.exception;

import com.toyproj.pinchhitterhomerun.type.ErrorMessage;

public class BrandException extends RuntimeException{
    public BrandException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
