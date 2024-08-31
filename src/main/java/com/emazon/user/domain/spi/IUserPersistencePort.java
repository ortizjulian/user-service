package com.emazon.user.domain.spi;

public interface IUserPersistencePort {
    Boolean existsByEmail(String email);

    Boolean existsByDocument(String document);
}
