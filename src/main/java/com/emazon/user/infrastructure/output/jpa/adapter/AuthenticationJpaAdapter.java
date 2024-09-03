package com.emazon.user.infrastructure.output.jpa.adapter;

import com.emazon.user.domain.model.User;
import com.emazon.user.domain.spi.IAuthenticationPersistencePort;
import com.emazon.user.infrastructure.output.jpa.entity.RoleEntity;
import com.emazon.user.infrastructure.output.jpa.entity.UserEntity;
import com.emazon.user.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.emazon.user.infrastructure.output.jpa.repository.IRoleRepository;
import com.emazon.user.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class AuthenticationJpaAdapter implements IAuthenticationPersistencePort {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final UserEntityMapper userEntityMapper;
    @Override
    public User register(User user) {
        Optional<RoleEntity> optionalRole = roleRepository.findById(user.getRole().getId());
        UserEntity userEntity = userEntityMapper.toEntity(user);
        optionalRole.ifPresent(userEntity::setRoleEntity);

        return userEntityMapper.toUser(userRepository.save(userEntity));
    }
}
