package com.emazon.user.application.mapper;

import com.emazon.user.application.dto.AuthenticationDtoResponse;
import com.emazon.user.domain.model.Authentication;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AuthenticationDtoResponseMapper {
    AuthenticationDtoResponse authenticationToAuthenticationDtoResponse(Authentication authentication);
}
