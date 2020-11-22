package com.toyproj.pinchhitterhomerun.exception;

import com.toyproj.pinchhitterhomerun.type.ErrorMessage;

public class BranchException extends RuntimeException{
    public BranchException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}