package com.coditas.multitenantelectricitymanagement.constants;

public final class ExceptionConstants {

    private ExceptionConstants() {
    }

    public static final String USER_NOT_FOUND = "USER_NOT_FOUND_FOR_PROVIDED_ID";
    public static final String RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND FOR PROVIDED ID";
    public static final String INVALID_CREDENTIAL = "INVALID_CREDENTIALS_SENT";
    public static final String UNAUTHENTICATED_USER = "USER_IS_NOT_AUTHENTICATED";
    public static final String DUPLICATE_RESOURCE = "THE_RESOURCE_ALREADY_EXIST";
    public static final String ROLE_MISMATCH = "NOT_A_VALID_ROLE_FOR_THIS_OPERATION";
    public static final String CUSTOMER_NOT_FOUND = "CUSTOMER_NOT_FOUND_FOR_PROVIDED_ID";
    public static final String BILLER_NOT_FOUND = "BILLER_NOT_FOUND";
    public static final String BILLER_ROLE_MISMATCH = "BILLER_ROLE_REQUIRED_FOR_THIS_OPERATION";
    public static final String DUPLICATE_EMPLOYEE_FOUND = "EMPLOYEE_WITH_GIVEN_ID_ALREADY_EXIST";
    public static final String BPO_NOT_FOUND = "BPO_WITH_GIVEN_ID_NOT_FOUND";
    public static final String STATE_NOT_FOUND = "STATE_WITH_GIVEN_ID_NOT_FOUND";
    public static final String DISTRICT_NOT_FOUND = "DISTRICT_WITH_GIVEN_ID_NOT_FOUND";
    public static final String CITY_NOT_FOUND = "CITY_WITH_GIVEN_ID_NOT_FOUND";
    public static final String CLIENT_NOT_FOUND = "CLIENT_WITH_GIVEN_ID_NOT_FOUND";
    public static final String BPO_ROLE_MISMATCH = "BPO_ROLE_REQUIRED_FOR_THIS_OPERATION";
    public static final String CUSTOMER_ROLE_MISMATCH = "CUSTOMER_ROLE_REQUIRED_FOR_THIS_OPERATION";
    public static final String CUSTOMER_QUERY_NOT_FOUND = "CUSTOMER_QUERY_NOT_FOUND";
    public static final String USER_ROLE_MISMATCH = "THE_RETRIEVED_USER_DOES_NOT_HAVE_REQUIRED_ROLE";

}