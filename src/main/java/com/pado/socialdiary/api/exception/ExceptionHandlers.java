package com.pado.socialdiary.api.exception;

import com.pado.socialdiary.api.common.dto.ExceptionDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ExceptionHandlers {

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity runtimeExceptionHandler(HttpServletRequest request, RuntimeException runtimeException) {
        ExceptionDto exceptionDto = new ExceptionDto(runtimeException.getMessage(), request.getRequestURI());

        return ResponseEntity
                .internalServerError()
                .body(exceptionDto);
    }
}
