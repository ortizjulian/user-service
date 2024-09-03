package com.emazon.user.domain.spi;

import com.emazon.user.domain.model.Authentication;
import com.emazon.user.domain.model.User;

public interface ISecurityPersistencePort {

    String encryptPassword(String password);
    Authentication getToken(User user);

}
