package com.pado.socialdiary.api.exception.member;

public class PasswordConfirmationException extends RuntimeException {

    private static final String MESSAGE = "Password is not matched";

    public PasswordConfirmationException() {
        super(MESSAGE);
    }
}
