package com.emazon.user.utils;

import static com.emazon.user.utils.Constants.UTILITY_CLASS_SHOULD_NOT_BE_INSTANTIATED;

public class SecurityConstants
{
    private SecurityConstants() {
        throw new UnsupportedOperationException(UTILITY_CLASS_SHOULD_NOT_BE_INSTANTIATED);
    }

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER ="Bearer ";
    public static final Integer JWT_SUBSTRING = 7;
    public static final Long JWT_EXPIRATION_MS =86400000L;

    //Jwt Claims
    public static final String CLAIM_ROLE = "role";
    public static final String CLAIM_ID = "id";

    //Roles names
    public static final String ROLE_WAREHOUSE_ASSISTANT = "Aux_bodega";
    public static final String ROLE_ADMIN = "Administrador";
    public static final String ROLE_CLIENT = "Cliente";

    //Auth
    public static final String EXCEPTION_INVALID_CREDENTIALS = "Invalid credentials provided";
    public static final String EXCEPTION_AUTHENTICATION_EXCEPTION = "Authentication exception occurred";
    public static final String EXCEPTION_ACCESS_DENIED= "Access Denied";
}
