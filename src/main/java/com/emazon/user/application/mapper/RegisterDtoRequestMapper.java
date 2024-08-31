package com.emazon.user.application.mapper;

import com.emazon.user.application.dto.RegisterDtoRequest;
import com.emazon.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RegisterDtoRequestMapper {
    User registerDtoRequestToUser(RegisterDtoRequest registerDtoRequest);
}
