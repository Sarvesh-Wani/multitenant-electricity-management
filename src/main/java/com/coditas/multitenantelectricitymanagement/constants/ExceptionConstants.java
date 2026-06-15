package com.coditas.multitenantelectricitymanagement.constants;

public final class ExceptionConstants {

    private ExceptionConstants() {
    }

    public static final String USERNAME_NOT_FOUND = "USERNAME_NOT_FOUND_EXCEPTION";
    public static final String RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND FOR PROVIDED ID";
    public static final String INVALID_CREDENTIAL = "INVALID_CREDENTIALS_SENT";
    public static final String UNAUTHENTICATED_USER = "USER_IS_NOT_AUTHENTICATED";
    public static final String DUPLICATE_RESOURCE = "THE_RESOURCE_ALREADY_EXIST";
    public static final String ROLE_MISMATCH = "NOT_A_VALID_ROLE_FOR_THIS_OPERATION";
    public static final String CUSTOMER_NOT_FOUND = "CUSTOMER_NOT_FOUND_FOR_PROVIDED_ID";

}