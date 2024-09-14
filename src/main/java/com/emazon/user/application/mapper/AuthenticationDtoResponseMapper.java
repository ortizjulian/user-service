package com.emazon.user.application.mapper;

import com.emazon.user.application.dto.AuthenticationDtoResponse;
import com.emazon.user.domain.model.Authentication;
import com.emazon.user.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = Constants.MAPPER_SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AuthenticationDtoResponseMapper {
    AuthenticationDtoResponse authenticationToAuthenticationDtoResponse(Authentication authentication);
}
