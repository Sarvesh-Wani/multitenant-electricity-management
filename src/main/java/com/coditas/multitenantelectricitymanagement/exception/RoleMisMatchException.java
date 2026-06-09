package com.coditas.multitenantelectricitymanagement.exception;

public class RoleMisMatchException extends RuntimeException {
    public RoleMisMatchException(String message) {
        super(message);
    }
}
