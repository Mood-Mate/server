package com.pado.socialdiary.api.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionDto {

    private String status = "fail";
    private String message;
    private String path;

    public ExceptionDto(String message, String path) {
        this.message = message;
        this.path = path;
    }
}
