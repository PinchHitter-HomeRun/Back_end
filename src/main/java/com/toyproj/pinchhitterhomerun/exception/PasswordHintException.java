package com.toyproj.pinchhitterhomerun.exception;

import com.toyproj.pinchhitterhomerun.type.ErrorMessage;

public class PasswordHintException  extends RuntimeException {
    public PasswordHintException(ErrorMessage error) {
        super(error.getMessage());
    }

}
