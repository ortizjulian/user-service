package com.emazon.user.application.mapper;

import com.emazon.user.application.dto.LoginDto;
import com.emazon.user.domain.model.Login;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface LoginDtoMapper {
    Login toLogin(LoginDto loginDto);
}
