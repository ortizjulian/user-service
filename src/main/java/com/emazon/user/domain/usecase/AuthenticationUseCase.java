package com.emazon.user.domain.usecase;

import com.emazon.user.domain.api.IAuthenticationServicePort;
import com.emazon.user.domain.exceptions.AgeNotValidException;
import com.emazon.user.domain.exceptions.DocumentAlreadyExistsException;
import com.emazon.user.domain.exceptions.EmailAlreadyExistsException;
import com.emazon.user.domain.exceptions.RoleNotFoundException;
import com.emazon.user.domain.model.Authentication;
import com.emazon.user.domain.model.User;
import com.emazon.user.domain.spi.IAuthenticationPersistencePort;
import com.emazon.user.domain.spi.IRolePersistencePort;
import com.emazon.user.domain.spi.IUserPersistencePort;
import com.emazon.user.utils.Constants;

import java.time.LocalDate;
import java.time.Period;

public class AuthenticationUseCase implements IAuthenticationServicePort {

    private final IAuthenticationPersistencePort authenticationPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final IUserPersistencePort userPersistencePort;

    public AuthenticationUseCase(IAuthenticationPersistencePort authenticationPersistencePort, IRolePersistencePort rolePersistencePort, IUserPersistencePort userPersistencePort) {
        this.authenticationPersistencePort = authenticationPersistencePort;
        this.rolePersistencePort = rolePersistencePort;
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public Authentication register(User user) {

        if(!rolePersistencePort.existsById(user.getRoleId())) {
            throw new RoleNotFoundException(Constants.EXCEPTION_ROLE_NOT_FOUND_BY_ID + user.getRoleId());
        }

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

        return authenticationPersistencePort.register(user);
    }
}
