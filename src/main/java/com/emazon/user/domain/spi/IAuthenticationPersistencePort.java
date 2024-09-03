package com.emazon.user.domain.spi;

import com.emazon.user.domain.model.User;

public interface IAuthenticationPersistencePort {
    User register(User user);
}
