package com.emazon.user.infrastructure.exceptionhandler;

import com.emazon.user.utils.Constants;

public enum ExceptionResponse {
    INVALID_JSON_FORMAT(Constants.EXCEPTION_INVALID_JSON_FORMAT),
    AGE_NOT_VALID(Constants.EXCEPTION_AGE_NOT_VALID),
    ROLE_NOT_FOUND(Constants.EXCEPTION_ROLE_NOT_FOUND),
    USER_NOT_FOUND(Constants.EXCEPTION_USER_NOT_FOUND),
    INVALID_CREDENTIALS(Constants.EXCEPTION_INVALID_CREDENTIALS),
    AUTHENTICATION_EXCEPTION(Constants.EXCEPTION_AUTHENTICATION_EXCEPTION);;
    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}