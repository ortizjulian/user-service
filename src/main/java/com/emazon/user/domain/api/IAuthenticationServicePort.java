package com.emazon.user.domain.api;

import com.emazon.user.domain.model.Authentication;
import com.emazon.user.domain.model.User;

public interface IAuthenticationServicePort {
    Authentication register(User user);

}
