package com.emazon.user.domain.spi;

import com.emazon.user.domain.model.User;

import java.util.Optional;

public interface IUserPersistencePort {
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    User register(User user);
    Boolean existsByDocument(String document);
}
