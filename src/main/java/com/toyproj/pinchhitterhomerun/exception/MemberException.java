package com.toyproj.pinchhitterhomerun.exception;

import com.toyproj.pinchhitterhomerun.type.ErrorMessage;

public class MemberException extends RuntimeException {

    public MemberException(ErrorMessage error) {
        super(error.getMessage());
    }
}
