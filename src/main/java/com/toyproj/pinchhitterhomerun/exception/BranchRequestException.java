package com.toyproj.pinchhitterhomerun.exception;

import com.toyproj.pinchhitterhomerun.type.ErrorMessage;

public class BranchRequestException extends RuntimeException{
    public BranchRequestException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }

    public BranchRequestException(String message) {
        super(message);
    }
}
