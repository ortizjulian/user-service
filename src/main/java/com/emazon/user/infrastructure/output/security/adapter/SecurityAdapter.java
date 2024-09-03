package com.emazon.user.infrastructure.output.security.adapter;

import com.emazon.user.domain.model.Authentication;
import com.emazon.user.domain.model.User;
import com.emazon.user.domain.spi.ISecurityPersistencePort;
import com.emazon.user.infrastructure.output.security.entity.SecurityUser;
import com.emazon.user.infrastructure.output.security.jwt.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class SecurityAdapter implements ISecurityPersistencePort {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenManager jwtTokenManager;

    @Override
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public Authentication getToken(User user) {
        Set<String> role = new HashSet<>();
        role.add(user.getRole().getName());
        SecurityUser securityUser = new SecurityUser(user.getEmail(), role);
        return new Authentication(jwtTokenManager.getToken(securityUser));
    }

}
