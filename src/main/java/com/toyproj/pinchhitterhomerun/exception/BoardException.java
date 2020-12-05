package com.toyproj.pinchhitterhomerun.exception;

import com.toyproj.pinchhitterhomerun.type.ErrorMessage;

public class BoardException extends RuntimeException{
    public BoardException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}