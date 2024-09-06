package com.emazon.user.domain.api;

import com.emazon.user.domain.model.Authentication;
import com.emazon.user.domain.model.Login;

public interface IAuthenticationServicePort {
    Authentication login(Login login);

}
