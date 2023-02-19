package com.pado.socialdiary.api.exception.member;

public class DuplicateEmailException extends RuntimeException {

    private static final String MESSAGE = "Duplicate Email";

    public DuplicateEmailException() {
        super(MESSAGE);
    }
}
