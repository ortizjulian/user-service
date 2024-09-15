package com.emazon.user.application.handler;

import com.emazon.user.application.dto.RegisterDtoRequest;
import com.emazon.user.domain.model.User;

public interface IUserHandler {
    User registerWareHouseAssistant(RegisterDtoRequest requisterRequest);
    User registerClient(RegisterDtoRequest registerDtoRequest);
}
