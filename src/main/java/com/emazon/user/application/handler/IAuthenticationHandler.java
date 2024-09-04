package com.emazon.user.application.handler;

import com.emazon.user.application.dto.AuthenticationDtoResponse;
import com.emazon.user.application.dto.LoginDto;

public interface IAuthenticationHandler {
    AuthenticationDtoResponse login(LoginDto loginDto);
}
