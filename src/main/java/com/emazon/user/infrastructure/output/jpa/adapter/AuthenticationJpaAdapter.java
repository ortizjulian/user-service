package com.emazon.user.infrastructure.output.jpa.adapter;

import com.emazon.user.domain.model.Authentication;
import com.emazon.user.domain.model.User;
import com.emazon.user.domain.spi.IAuthenticationPersistencePort;
import com.emazon.user.infrastructure.output.jpa.entity.RoleEntity;
import com.emazon.user.infrastructure.output.jpa.entity.UserEntity;
import com.emazon.user.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.emazon.user.infrastructure.output.jpa.repository.IRoleRepository;
import com.emazon.user.infrastructure.output.jpa.repository.IUserRepository;
import com.emazon.user.infrastructure.output.security.jwt.JwtAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RequiredArgsConstructor
public class AuthenticationJpaAdapter implements IAuthenticationPersistencePort {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAdapter jwtAdapter;
    private final UserEntityMapper userEntityMapper;
    @Override
    public Authentication register(User user) {

        Optional<RoleEntity> optionalRole = roleRepository.findById(user.getRoleId());
        UserEntity userEntity = userEntityMapper.toEntity(user);
        optionalRole.ifPresent(userEntity::setRoleEntity);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(userEntity);
        return new Authentication(jwtAdapter.getToken(userEntity));
    }
}
