package com.pado.socialdiary.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(Exception.class)
    public ResponseEntity runtimeExceptionHandler(Exception exception) {
        return new ResponseEntity(exception.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalArgumentExceptionHandler(IllegalArgumentException illegalArgumentException) {
        return new ResponseEntity(illegalArgumentException.getMessage(), BAD_REQUEST);
    }
}
