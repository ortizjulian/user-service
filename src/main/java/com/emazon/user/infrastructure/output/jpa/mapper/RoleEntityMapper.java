package com.emazon.user.infrastructure.output.jpa.mapper;

import com.emazon.user.domain.model.Role;
import com.emazon.user.infrastructure.output.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RoleEntityMapper {
    Role toRole(RoleEntity roleEntity);
}
