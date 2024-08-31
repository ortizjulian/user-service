package com.emazon.user.application.handler;

import com.emazon.user.application.dto.AuthenticationDtoResponse;
import com.emazon.user.application.dto.RegisterDtoRequest;

public interface IAuthenticationHandler {
    AuthenticationDtoResponse register(RegisterDtoRequest requisterRequest);
}
