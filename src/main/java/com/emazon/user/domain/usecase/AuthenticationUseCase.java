package com.emazon.user.domain.usecase;

import com.emazon.user.domain.api.IAuthenticationServicePort;
import com.emazon.user.domain.exceptions.*;
import com.emazon.user.domain.model.Authentication;
import com.emazon.user.domain.model.Login;
import com.emazon.user.domain.model.User;
import com.emazon.user.domain.spi.ISecurityPersistencePort;
import com.emazon.user.domain.spi.IUserPersistencePort;

import java.util.Optional;

public class AuthenticationUseCase implements IAuthenticationServicePort {

    private IUserPersistencePort userPersistencePort;
    private ISecurityPersistencePort securityPersistencePort;

    public AuthenticationUseCase( IUserPersistencePort userPersistencePort, ISecurityPersistencePort securityPersistencePort) {
        this.userPersistencePort = userPersistencePort;
        this.securityPersistencePort = securityPersistencePort;
    }

    @Override
    public Authentication login(Login login) {
        Optional<User> authUser= userPersistencePort.findByEmail(login.getEmail());

        if(authUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        securityPersistencePort.authenthicate(login);

        return securityPersistencePort.getToken(authUser.get());

    }
}
