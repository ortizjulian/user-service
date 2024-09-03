package com.emazon.user.infrastructure.output.jpa.adapter;

import com.emazon.user.domain.spi.IUserPersistencePort;
import com.emazon.user.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByDocument(String document) {
        return userRepository.existsByDocument(document);
    }
}
