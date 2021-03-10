package com.toyproj.pinchhitterhomerun.exception;

import com.toyproj.pinchhitterhomerun.type.ErrorMessage;

public class RoleException extends RuntimeException {
    public RoleException(ErrorMessage error) {
        super(error.getMessage());
    }
}
