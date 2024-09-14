package com.emazon.user.infrastructure.output.jpa.mapper;

import com.emazon.user.domain.model.Role;
import com.emazon.user.infrastructure.output.jpa.entity.RoleEntity;
import com.emazon.user.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = Constants.MAPPER_SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RoleEntityMapper {
    Role toRole(RoleEntity roleEntity);
}
