package com.coditas.multitenantelectricitymanagement.exception;

public class UnAuthenticatedUserException extends RuntimeException {
    public UnAuthenticatedUserException(String message) {
        super(message);
    }
}
