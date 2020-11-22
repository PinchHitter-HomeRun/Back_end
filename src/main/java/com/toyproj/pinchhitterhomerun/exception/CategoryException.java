package com.toyproj.pinchhitterhomerun.exception;

import com.toyproj.pinchhitterhomerun.type.ErrorMessage;

public class CategoryException extends RuntimeException{
    public CategoryException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
