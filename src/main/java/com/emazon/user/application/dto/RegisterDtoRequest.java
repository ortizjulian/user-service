package com.emazon.user.application.dto;

import com.emazon.user.utils.Constants;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class RegisterDtoRequest {

    @NotBlank(message = Constants.EXCEPTION_USER_NAME_MANDATORY)
    private String name;

    @NotBlank(message = Constants.EXCEPTION_USER_LAST_NAME_MANDATORY)
    private String lastName;

    @NotBlank(message = Constants.EXCEPTION_USER_PASSWORD_MANDATORY)
    @Size(min = Constants.MIN_USER_PASSWORD_CHARACTERS, message = Constants.EXCEPTION_USER_PASSWORD_MIN_LENGTH)
    private String password;

    @NotBlank(message = Constants.EXCEPTION_USER_EMAIL_MANDATORY)
    @Email(message = Constants.EXCEPTION_USER_EMAIL_INVALID)
    private String email;

    @NotBlank(message = Constants.EXCEPTION_USER_DOCUMENT_MANDATORY)
    @Pattern(regexp = "\\d+", message = Constants.EXCEPTION_USER_DOCUMENT_NUMERIC)
    private String document;

    @NotBlank(message = Constants.EXCEPTION_USER_PHONE_MANDATORY)
    @Size(max = Constants.MAX_USER_PHONE_CHARACTERS, message = Constants.EXCEPTION_USER_PHONE_MAX_LENGTH)
    @Pattern(regexp = "^\\+?\\d+$", message = Constants.EXCEPTION_USER_PHONE_INVALID)
    private String phone;

    @NotNull(message = Constants.EXCEPTION_USER_BIRTHDATE_MANDATORY)
    private LocalDate birthDate;

    @NotNull(message = Constants.EXCEPTION_USER_ROLE_ID_MANDATORY)
    private Long roleId;

    public RegisterDtoRequest(String name, String lastName, String password, String email, String document, String phone, LocalDate birthDate, Long roleId) {
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.document = document;
        this.phone = phone;
        this.birthDate = birthDate;
        this.roleId = roleId;
    }

    public @NotBlank(message = "Name is mandatory") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is mandatory") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Last name is mandatory") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Last name is mandatory") String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank(message = "Password is mandatory") @Size(min = 8, message = "Password must be at least 8 characters long") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is mandatory") @Size(min = 8, message = "Password must be at least 8 characters long") String password) {
        this.password = password;
    }

    public @NotBlank(message = "Email is mandatory") @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is mandatory") @Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Document is mandatory") @Pattern(regexp = "\\d+", message = "Document must contain only numbers") String getDocument() {
        return document;
    }

    public void setDocument(@NotBlank(message = "Document is mandatory") @Pattern(regexp = "\\d+", message = "Document must contain only numbers") String document) {
        this.document = document;
    }

    public @NotBlank(message = "Phone is mandatory") @Size(max = 13, message = "Phone number must be no more than 13 characters long") @Pattern(regexp = "^\\+?\\d+$", message = "Phone must contain only numbers and an optional leading '+'") String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank(message = "Phone is mandatory") @Size(max = 13, message = "Phone number must be no more than 13 characters long") @Pattern(regexp = "^\\+?\\d+$", message = "Phone must contain only numbers and an optional leading '+'") String phone) {
        this.phone = phone;
    }

    public @NotNull(message = "Birthdate is mandatory") LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(@NotNull(message = "Birthdate is mandatory") LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public @NotNull(message = "Role ID is mandatory") Long getRoleId() {
        return roleId;
    }

    public void setRoleId(@NotNull(message = "Role ID is mandatory") Long roleId) {
        this.roleId = roleId;
    }
}
