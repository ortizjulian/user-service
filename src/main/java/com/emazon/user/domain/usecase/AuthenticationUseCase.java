package com.emazon.user.domain.usecase;

import com.emazon.user.domain.api.IAuthenticationServicePort;
import com.emazon.user.domain.exceptions.AgeNotValidException;
import com.emazon.user.domain.exceptions.DocumentAlreadyExistsException;
import com.emazon.user.domain.exceptions.EmailAlreadyExistsException;
import com.emazon.user.domain.exceptions.RoleNotFoundException;
import com.emazon.user.domain.model.Authentication;
import com.emazon.user.domain.model.Role;
import com.emazon.user.domain.model.User;
import com.emazon.user.domain.spi.IAuthenticationPersistencePort;
import com.emazon.user.domain.spi.IRolePersistencePort;
import com.emazon.user.domain.spi.ISecurityPersistencePort;
import com.emazon.user.domain.spi.IUserPersistencePort;
import com.emazon.user.utils.Constants;

import java.time.LocalDate;
import java.time.Period;

public class AuthenticationUseCase implements IAuthenticationServicePort {

    private final IAuthenticationPersistencePort authenticationPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final IUserPersistencePort userPersistencePort;
    private final ISecurityPersistencePort securityPersistencePort;

    public AuthenticationUseCase(IAuthenticationPersistencePort authenticationPersistencePort, IRolePersistencePort rolePersistencePort, IUserPersistencePort userPersistencePort, ISecurityPersistencePort securityPersistencePort) {
        this.authenticationPersistencePort = authenticationPersistencePort;
        this.rolePersistencePort = rolePersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.securityPersistencePort = securityPersistencePort;
    }

    @Override
    public Authentication register(User user, String roleName) {

        if (userPersistencePort.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(Constants.EXCEPTION_EMAIL_ALREADY_EXISTS + user.getEmail());
        }

        if (userPersistencePort.existsByDocument(user.getDocument())) {
            throw new DocumentAlreadyExistsException(Constants.EXCEPTION_DOCUMENT_ALREADY_EXISTS + user.getDocument());
        }

        Boolean isAdult = Period.between(user.getBirthDate(), LocalDate.now()).getYears() >= 18;

        if(!isAdult) {
            throw new AgeNotValidException();
        }
        Role role = rolePersistencePort.findByName(roleName).orElseThrow(RoleNotFoundException::new);

        user.setRole(role);

        user.setPassword(securityPersistencePort.encryptPassword(user.getPassword()));
        User savedUser =  authenticationPersistencePort.register(user);

        return securityPersistencePort.getToken(savedUser);
    }
}
