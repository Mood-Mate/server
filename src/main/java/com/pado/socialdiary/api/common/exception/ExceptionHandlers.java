package com.pado.socialdiary.api.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice(basePackages = "com.pado.socialDiary.*")
public class ExceptionHandlers {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity runtimeExceptionHandler(RuntimeException runtimeException) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(runtimeException.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalArgumentExceptionHandler(IllegalArgumentException illegalArgumentException) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(illegalArgumentException.getMessage());
    }
}
