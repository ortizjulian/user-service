package com.emazon.user.domain.usecase;

import com.emazon.user.domain.exceptions.AgeNotValidException;
import com.emazon.user.domain.exceptions.DocumentAlreadyExistsException;
import com.emazon.user.domain.exceptions.EmailAlreadyExistsException;
import com.emazon.user.domain.model.Role;
import com.emazon.user.domain.model.User;
import com.emazon.user.domain.spi.IRolePersistencePort;
import com.emazon.user.domain.spi.ISecurityPersistencePort;
import com.emazon.user.domain.spi.IUserPersistencePort;
import com.emazon.user.utils.Constants;
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
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @Mock
    private ISecurityPersistencePort securityPersistencePort;

    @InjectMocks
    private UserUseCase userUseCase;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void AuthenticationUseCase_Register_WhenCalled_ShouldCallSaveOnPersistencePort() {
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
        Mockito.when(rolePersistencePort.findByName(Constants.ROLE_WAREHOUSE_ASSISTANT)).thenReturn(Optional.of(new Role(1L, "Cliente", "Rol CLiente")));

        User registeredUser = userUseCase.register(user, Constants.ROLE_WAREHOUSE_ASSISTANT);
        assertEquals(user, registeredUser);
    }

    @Test
    void AuthenticationUseCase_Register_WhenUserIsNotAdult_ShouldThrowExceptionAgeNotValid() {
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

        assertThrows(AgeNotValidException.class, () -> {
            userUseCase.register(user, Constants.ROLE_WAREHOUSE_ASSISTANT);
        });

    }

    @Test
    void AuthenticationUseCase_Register_WhenEmailAlreadyExists_ShouldThrowExceptionEmailAlreadyExists() {
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

        Mockito.when(userPersistencePort.existsByEmail("julian.ortiz@gmail.com")).thenReturn(true);

       assertThrows(EmailAlreadyExistsException.class, () -> {
           userUseCase.register(user, Constants.ROLE_WAREHOUSE_ASSISTANT);
        });
    }

    @Test
    void AuthenticationUseCase_Register_WhenDocumentAlreadyExists_ShouldThrowExceptionExceptionDocumentAlreadyExists() {
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
        Mockito.when(userPersistencePort.existsByDocument("10203040")).thenReturn(true);

        assertThrows(DocumentAlreadyExistsException.class, () -> {
            userUseCase.register(user, Constants.ROLE_WAREHOUSE_ASSISTANT);
        });

    }

}