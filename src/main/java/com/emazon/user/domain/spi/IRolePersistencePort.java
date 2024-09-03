package com.emazon.user.domain.spi;

import com.emazon.user.domain.model.Role;

import java.util.Optional;

public interface IRolePersistencePort {
    Boolean existsById(Long roleId);
    Optional<Role> findByName(String roleName);
}
