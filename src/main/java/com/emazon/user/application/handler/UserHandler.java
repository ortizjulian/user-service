package com.emazon.user.application.handler;

import com.emazon.user.application.dto.RegisterDtoRequest;
import com.emazon.user.application.mapper.RegisterDtoRequestMapper;
import com.emazon.user.domain.api.IUserServicePort;
import com.emazon.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final RegisterDtoRequestMapper registerDtoRequestMapper;

    @Override
    public User registerWareHouseAssistant(RegisterDtoRequest registerDtoRequest) {
        User user = registerDtoRequestMapper.registerDtoRequestToUser(registerDtoRequest);
        return userServicePort.registerWareHouseAssistant(user);
    }

    @Override
    public User registerClient(RegisterDtoRequest registerDtoRequest) {
        User user = registerDtoRequestMapper.registerDtoRequestToUser(registerDtoRequest);
        return userServicePort.registerClient(user);
    }
}
