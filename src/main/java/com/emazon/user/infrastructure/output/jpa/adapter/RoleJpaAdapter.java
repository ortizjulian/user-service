package com.emazon.user.infrastructure.output.jpa.adapter;

import com.emazon.user.domain.spi.IRolePersistencePort;
import com.emazon.user.infrastructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {
    private final IRoleRepository roleRepository;

    @Override
    public Boolean existsById(Long roleId) {
        return roleRepository.existsById(roleId);
    }
}
