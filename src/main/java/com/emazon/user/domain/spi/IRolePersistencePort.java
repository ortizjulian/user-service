package com.emazon.user.domain.spi;

public interface IRolePersistencePort {
    Boolean existsById(Long roleId);
}
