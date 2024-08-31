package com.emazon.user.infrastructure.output.jpa.repository;

import com.emazon.user.infrastructure.output.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByName(String name);
    Boolean existsByEmail(String email);
    Boolean existsByDocument(String document);
}
