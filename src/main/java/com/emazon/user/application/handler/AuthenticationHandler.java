package com.emazon.user.application.handler;

import com.emazon.user.application.dto.AuthenticationDtoResponse;
import com.emazon.user.application.dto.LoginDto;
import com.emazon.user.application.mapper.AuthenticationDtoResponseMapper;
import com.emazon.user.application.mapper.LoginDtoMapper;
import com.emazon.user.domain.api.IAuthenticationServicePort;
import com.emazon.user.domain.model.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationHandler implements IAuthenticationHandler {

    private final IAuthenticationServicePort authenticationServicePort;
    private final AuthenticationDtoResponseMapper authenticationDtoResponseMapper;
    private final LoginDtoMapper loginDtoMapper;

    @Override
    public AuthenticationDtoResponse login(LoginDto loginDto) {
        Login login = loginDtoMapper.toLogin(loginDto);
        return authenticationDtoResponseMapper.authenticationToAuthenticationDtoResponse(authenticationServicePort.login(login));
    }
}
