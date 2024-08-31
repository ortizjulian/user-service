package com.emazon.user.infrastructure.exceptionhandler;

public enum ExceptionResponse {
    EXAMPLE_EXCEPTION("");
    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}