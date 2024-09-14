package com.emazon.user.infrastructure.output.jpa.mapper;

import com.emazon.user.domain.model.User;
import com.emazon.user.infrastructure.output.jpa.entity.UserEntity;
import com.emazon.user.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = Constants.MAPPER_SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface UserEntityMapper {
    @Mapping(target = "roleEntity", ignore = true)
    UserEntity toEntity(User article);

    @Mapping(source = "roleEntity", target = "role")
    User toUser(UserEntity userEntity);
}
