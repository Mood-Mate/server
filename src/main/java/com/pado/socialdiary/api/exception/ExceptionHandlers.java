package com.pado.socialdiary.api.exception;

import com.pado.socialdiary.api.common.dto.ExceptionDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionHandlers {

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity runtimeExceptionHandler(HttpServletRequest request, RuntimeException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.toString(), request.getRequestURI());

        log.error("[ exception handling ] ={}", e);

        return ResponseEntity
                .internalServerError()
                .body(exceptionDto);
    }
}
