package com.emazon.user.application.mapper;

import com.emazon.user.application.dto.RegisterDtoRequest;
import com.emazon.user.domain.model.User;
import com.emazon.user.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = Constants.MAPPER_SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RegisterDtoRequestMapper {
    User registerDtoRequestToUser(RegisterDtoRequest registerDtoRequest);
}
