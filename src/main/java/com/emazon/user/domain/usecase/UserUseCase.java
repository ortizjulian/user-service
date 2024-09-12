package com.emazon.user.domain.usecase;

import com.emazon.user.domain.api.IUserServicePort;
import com.emazon.user.domain.exceptions.AgeNotValidException;
import com.emazon.user.domain.exceptions.DocumentAlreadyExistsException;
import com.emazon.user.domain.exceptions.EmailAlreadyExistsException;
import com.emazon.user.domain.exceptions.RoleNotFoundException;
import com.emazon.user.domain.model.Role;
import com.emazon.user.domain.model.User;
import com.emazon.user.domain.spi.IRolePersistencePort;
import com.emazon.user.domain.spi.ISecurityPersistencePort;
import com.emazon.user.domain.spi.IUserPersistencePort;
import com.emazon.user.utils.Constants;

import java.time.LocalDate;
import java.time.Period;

public class UserUseCase implements IUserServicePort {

    private IRolePersistencePort rolePersistencePort;
    private IUserPersistencePort userPersistencePort;
    private ISecurityPersistencePort securityPersistencePort;

    public UserUseCase( IRolePersistencePort rolePersistencePort, IUserPersistencePort userPersistencePort, ISecurityPersistencePort securityPersistencePort) {
        this.rolePersistencePort = rolePersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.securityPersistencePort = securityPersistencePort;
    }

    @Override
    public User register(User user, String roleName) {
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
        return userPersistencePort.register(user);
    }
}
