package com.emazon.user.infrastructure.output.jpa.adapter;

import com.emazon.user.domain.model.Role;
import com.emazon.user.domain.spi.IRolePersistencePort;
import com.emazon.user.infrastructure.output.jpa.entity.RoleEntity;
import com.emazon.user.infrastructure.output.jpa.mapper.RoleEntityMapper;
import com.emazon.user.infrastructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {
    private final IRoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;
    @Override
    public Boolean existsById(Long roleId) {
        return roleRepository.existsById(roleId);
    }

    @Override
    public Optional<Role> findByName(String roleName) {
        Optional<RoleEntity> roleEntityOptional = roleRepository.findByName(roleName);

        if (roleEntityOptional.isPresent()) {
            RoleEntity roleEntity = roleEntityOptional.get();
            Role role = roleEntityMapper.toRole(roleEntity);
            return Optional.of(role);
        } else {
            return Optional.empty();
        }
    }
}
