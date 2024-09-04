package com.emazon.user.application.handler;

import com.emazon.user.application.dto.RegisterDtoRequest;
import com.emazon.user.application.mapper.AuthenticationDtoResponseMapper;
import com.emazon.user.application.mapper.RegisterDtoRequestMapper;
import com.emazon.user.domain.api.IUserServicePort;
import com.emazon.user.domain.model.User;
import com.emazon.user.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final RegisterDtoRequestMapper registerDtoRequestMapper;
    private final AuthenticationDtoResponseMapper authenticationDtoResponseMapper;

    @Override
    public User registerWareHouseAssistan(RegisterDtoRequest registerRequest) {
        User user = registerDtoRequestMapper.registerDtoRequestToUser(registerRequest);
        return userServicePort.register(user, Constants.ROLE_WAREHOUSEASSISTANT);

    }
}
