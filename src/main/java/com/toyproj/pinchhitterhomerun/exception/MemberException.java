package com.toyproj.pinchhitterhomerun.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class MemberException extends Exception {

    public MemberException(String message) {
        super(message);
    }

}
