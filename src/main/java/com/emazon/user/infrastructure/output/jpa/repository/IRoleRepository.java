package com.emazon.user.infrastructure.output.jpa.repository;

import com.emazon.user.infrastructure.output.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IRoleRepository extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByName(String roleName);
}
