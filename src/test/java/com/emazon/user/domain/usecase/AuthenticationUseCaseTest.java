package com.emazon.user.domain.usecase;

import com.emazon.user.domain.exceptions.AgeNotValidException;
import com.emazon.user.domain.exceptions.DocumentAlreadyExistsException;
import com.emazon.user.domain.exceptions.EmailAlreadyExistsException;
import com.emazon.user.domain.model.Authentication;
import com.emazon.user.domain.model.Role;
import com.emazon.user.domain.model.User;
import com.emazon.user.domain.spi.IRolePersistencePort;
import com.emazon.user.domain.spi.ISecurityPersistencePort;
import com.emazon.user.domain.spi.IUserPersistencePort;
import com.emazon.user.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @Mock
    private ISecurityPersistencePort securityPersistencePort;

    @InjectMocks
    private AuthenticationUseCase authenticationUseCase;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void AuthenticationUseCae_Register_ShouldReturnTokenWhenUserIsRegistered() {
        User user = new User(
                null,
                "Julian",
                "Ortiz",
                "SEGURA",
                "julian.ortiz@gmail.com",
                "10203040",
                "192939",
                LocalDate.of(2000, 10, 10),
                null
        );
        Mockito.when(userPersistencePort.existsByEmail("julian.ortiz@gmail.com")).thenReturn(false);
        Mockito.when(userPersistencePort.existsByDocument("10203040")).thenReturn(false);
        Mockito.when(securityPersistencePort.encryptPassword("SEGURA")).thenReturn("encryptedPassword");
        Mockito.when(userPersistencePort.register(user)).thenReturn(user);
        Mockito.when(securityPersistencePort.getToken(user)).thenReturn(new Authentication("token"));
        Mockito.when(rolePersistencePort.findByName(Constants.ROLE_WAREHOUSEASSISTANT)).thenReturn(Optional.of(new Role(1L, "Cliente", "Rol CLiente")));
        Authentication response = authenticationUseCase.register(user, Constants.ROLE_WAREHOUSEASSISTANT);

        assertEquals("token", response.getToken());
    }

    @Test
    void AuthenticationUseCase_Register_ShouldThrowExceptionWhenUserIsNotAdult() {
        User user = new User(
                null,
                "Julian",
                "Ortiz",
                "SEGURA",
                "julian.ortiz@gmail.com",
                "10203040",
                "192939",
                LocalDate.of(2020, 10, 10),
                null
        );

        Mockito.when(userPersistencePort.existsByEmail("julian.ortiz@gmail.com")).thenReturn(false);
        Mockito.when(userPersistencePort.existsByDocument("10203040")).thenReturn(false);
        Mockito.when(securityPersistencePort.encryptPassword("SEGURA")).thenReturn("encryptedPassword");
        Mockito.when(rolePersistencePort.findByName(Constants.ROLE_WAREHOUSEASSISTANT)).thenReturn(Optional.of(new Role(1L, "Cliente", "Rol CLiente")));

        assertThrows(AgeNotValidException.class, () -> {
            authenticationUseCase.register(user, Constants.ROLE_WAREHOUSEASSISTANT);
        });

    }

    @Test
    void AuthenticationUseCase_Register_ShouldThrowExceptionWhenEmailAlreadyExists() {
        User user = new User(
                null,
                "Julian",
                "Ortiz",
                "SEGURA",
                "julian.ortiz@gmail.com",
                "10203040",
                "192939",
                LocalDate.of(2000, 10, 10),
                null
        );

        Mockito.when(userPersistencePort.existsByEmail("julian.ortiz@gmail.com")).thenReturn(true); // Correo ya existe
        Mockito.when(userPersistencePort.existsByDocument("10203040")).thenReturn(false);
        Mockito.when(securityPersistencePort.encryptPassword("SEGURA")).thenReturn("encryptedPassword");
        Mockito.when(rolePersistencePort.findByName(Constants.ROLE_WAREHOUSEASSISTANT)).thenReturn(Optional.of(new Role(1L, "Cliente", "Rol CLiente")));

       assertThrows(EmailAlreadyExistsException.class, () -> {
            authenticationUseCase.register(user, Constants.ROLE_WAREHOUSEASSISTANT);
        });
    }

    @Test
    void AuthenticationUseCase_Register_ShouldThrowExceptionWhenDocumentAlreadyExists() {
        User user = new User(
                null,
                "Julian",
                "Ortiz",
                "SEGURA",
                "julian.ortiz@gmail.com",
                "10203040",
                "192939",
                LocalDate.of(2000, 10, 10),
                null
        );

        Mockito.when(userPersistencePort.existsByEmail("julian.ortiz@gmail.com")).thenReturn(false);
        Mockito.when(userPersistencePort.existsByDocument("10203040")).thenReturn(true); // Documento ya existe
        Mockito.when(securityPersistencePort.encryptPassword("SEGURA")).thenReturn("encryptedPassword");
        Mockito.when(rolePersistencePort.findByName(Constants.ROLE_WAREHOUSEASSISTANT)).thenReturn(Optional.of(new Role(1L, "Cliente", "Rol CLiente")));

        assertThrows(DocumentAlreadyExistsException.class, () -> {
            authenticationUseCase.register(user, Constants.ROLE_WAREHOUSEASSISTANT);
        });

    }

}