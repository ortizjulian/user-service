package com.emazon.user.infrastructure.configuration;

import com.emazon.user.domain.api.IAuthenticationServicePort;
import com.emazon.user.domain.api.IUserServicePort;
import com.emazon.user.domain.spi.IRolePersistencePort;
import com.emazon.user.domain.spi.ISecurityPersistencePort;
import com.emazon.user.domain.spi.IUserPersistencePort;
import com.emazon.user.domain.usecase.AuthenticationUseCase;
import com.emazon.user.domain.usecase.UserUseCase;
import com.emazon.user.infrastructure.output.jpa.adapter.RoleJpaAdapter;
import com.emazon.user.infrastructure.output.jpa.adapter.UserJpaAdapter;
import com.emazon.user.infrastructure.output.jpa.entity.UserEntity;
import com.emazon.user.infrastructure.output.jpa.mapper.RoleEntityMapper;
import com.emazon.user.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.emazon.user.infrastructure.output.jpa.repository.IRoleRepository;
import com.emazon.user.infrastructure.output.jpa.repository.IUserRepository;
import com.emazon.user.infrastructure.output.security.adapter.SecurityAdapter;
import com.emazon.user.infrastructure.output.security.entity.SecurityUser;
import com.emazon.user.infrastructure.output.security.jwt.JwtTokenManager;
import com.emazon.user.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final UserEntityMapper userEntityMapper;
    private final RoleEntityMapper roleEntityMapper;
    private final JwtTokenManager jwtTokenManager;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public IRolePersistencePort rolePersistencePort(){
        return new RoleJpaAdapter(roleRepository,roleEntityMapper);
    }

    @Bean
    public IUserPersistencePort userPersistencePort(){
        return new UserJpaAdapter(userRepository,userEntityMapper,roleRepository);
    }

    @Bean
    public IAuthenticationServicePort authenticationServicePort() throws Exception {
        return new AuthenticationUseCase(userPersistencePort(),securityPersistencePort() );
    }

    @Bean
    public IUserServicePort userServicePort() throws Exception {
        return new UserUseCase(rolePersistencePort(),userPersistencePort(),securityPersistencePort());
    }

    @Bean
    public ISecurityPersistencePort securityPersistencePort() throws Exception {
        return new SecurityAdapter(encoder(),jwtTokenManager,authenticationManager(authenticationConfiguration));
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            UserEntity userEntity = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(Constants.EXCEPTION_USER_NOT_FOUND));
            Set<String> role = new HashSet<>();
            role.add(userEntity.getRoleEntity().getName());
            return new SecurityUser(userEntity.getId(),userEntity.getEmail(),userEntity.getPassword(), role);
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
