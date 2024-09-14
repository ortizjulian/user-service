package com.emazon.user.domain.api;

import com.emazon.user.domain.model.User;

public interface IUserServicePort {
    User registerWareHouseAssistant(User user);
    User registerClient(User user);
}
