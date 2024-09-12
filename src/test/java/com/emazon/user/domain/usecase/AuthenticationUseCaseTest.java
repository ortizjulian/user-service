package com.emazon.user.domain.usecase;

import com.emazon.user.domain.exceptions.UserNotFoundException;
import com.emazon.user.domain.model.Authentication;
import com.emazon.user.domain.model.Login;
import com.emazon.user.domain.model.User;
import com.emazon.user.domain.spi.ISecurityPersistencePort;
import com.emazon.user.domain.spi.IUserPersistencePort;
import com.emazon.user.infrastructure.exceptions.InvalidCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private ISecurityPersistencePort securityPersistencePort;

    @InjectMocks
    private AuthenticationUseCase authenticationUseCase;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void AuthenticationUseCase_Login_WhenValidCredentials_ShouldReturnToken(){
        Login login = new Login("julianito@ortixs@gmail.com", "12345678");
        User user = new User(
                null,
                "Julian",
                "Ortiz",
                "12345678",
                "julianito@ortixs@gmail.com",
                "10203040",
                "192939",
                LocalDate.of(2020, 10, 10),
                null
        );

        Mockito.when(userPersistencePort.findByEmail(login.getEmail())).thenReturn(Optional.of(user));
        Mockito.doNothing().when(securityPersistencePort).authenthicate(login);

        Authentication authentication = new Authentication("GeneratedToken");

        Mockito.when(securityPersistencePort.getToken(user)).thenReturn(authentication);

        Authentication result = authenticationUseCase.login(login);

        assertNotNull(result.getToken());
        assertEquals("GeneratedToken", result.getToken());
    }

    @Test
    void AuthenticationUseCase_Login_WhenEmailDoesNotExists_ShouldThrowExceptionUserNotFound(){
        Login login = new Login("julianito@ortixs@gmail.com", "12345678");

        Mockito.when(userPersistencePort.findByEmail(login.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            authenticationUseCase.login(login);
        });
    }

    @Test
    void AuthenticationUseCase_Login_WhenWrongPassword_ShouldThrowExceptionInvalidCredentials(){
        Login login = new Login("julianito@ortixs@gmail.com", "12345678");

        Mockito.when(userPersistencePort.findByEmail(login.getEmail())).thenReturn(Optional.empty());

        User user = new User(
                null,
                "Julian",
                "Ortiz",
                "SEGURA",
                "julianito@ortixs@gmail.com",
                "10203040",
                "192939",
                LocalDate.of(2020, 10, 10),
                null
        );

        Mockito.when(userPersistencePort.findByEmail(login.getEmail())).thenReturn(Optional.of(user));
        Mockito.doThrow(InvalidCredentialsException.class).when(securityPersistencePort).authenthicate(login);

        assertThrows(InvalidCredentialsException.class, () -> {
            authenticationUseCase.login(login);
        });
    }
}