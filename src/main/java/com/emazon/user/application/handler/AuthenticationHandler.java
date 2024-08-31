package com.emazon.user.application.handler;

import com.emazon.user.application.dto.AuthenticationDtoResponse;
import com.emazon.user.application.dto.RegisterDtoRequest;
import com.emazon.user.application.mapper.AuthenticationDtoResponseMapper;
import com.emazon.user.application.mapper.RegisterDtoRequestMapper;
import com.emazon.user.domain.api.IAuthenticationServicePort;
import com.emazon.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationHandler implements IAuthenticationHandler {

    private final IAuthenticationServicePort authenticationServicePort;
    private final RegisterDtoRequestMapper registerDtoRequestMapper;
    private final AuthenticationDtoResponseMapper authenticationDtoResponseMapper;
    @Override
    public AuthenticationDtoResponse register(RegisterDtoRequest registerRequest) {
        User user = registerDtoRequestMapper.registerDtoRequestToUser(registerRequest);
        return authenticationDtoResponseMapper.authenticationToAuthenticationDtoResponse(authenticationServicePort.register(user));

    }
}
