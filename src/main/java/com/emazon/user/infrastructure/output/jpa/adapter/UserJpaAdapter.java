package com.emazon.user.infrastructure.output.jpa.adapter;

import com.emazon.user.domain.model.User;
import com.emazon.user.domain.spi.IUserPersistencePort;
import com.emazon.user.infrastructure.output.jpa.entity.RoleEntity;
import com.emazon.user.infrastructure.output.jpa.entity.UserEntity;
import com.emazon.user.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.emazon.user.infrastructure.output.jpa.repository.IRoleRepository;
import com.emazon.user.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final IRoleRepository roleRepository;

    @Override
    public User register(User user) {
        Optional<RoleEntity> optionalRole = roleRepository.findById(user.getRole().getId());
        UserEntity userEntity = userEntityMapper.toEntity(user);
        optionalRole.ifPresent(userEntity::setRoleEntity);

        return userEntityMapper.toUser(userRepository.save(userEntity));
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            User user = userEntityMapper.toUser(userEntity);
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Boolean existsByDocument(String document) {
        return userRepository.existsByDocument(document);
    }
}
