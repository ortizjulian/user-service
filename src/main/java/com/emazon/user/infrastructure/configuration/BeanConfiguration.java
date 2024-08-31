package com.emazon.user.infrastructure.configuration;

import com.emazon.user.domain.api.IAuthenticationServicePort;
import com.emazon.user.domain.spi.IAuthenticationPersistencePort;
import com.emazon.user.domain.spi.IRolePersistencePort;
import com.emazon.user.domain.spi.IUserPersistencePort;
import com.emazon.user.domain.usecase.AuthenticationUseCase;
import com.emazon.user.infrastructure.output.jpa.adapter.AuthenticationJpaAdapter;
import com.emazon.user.infrastructure.output.jpa.adapter.RoleJpaAdapter;
import com.emazon.user.infrastructure.output.jpa.adapter.UserJpaAdapter;
import com.emazon.user.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.emazon.user.infrastructure.output.jpa.repository.IRoleRepository;
import com.emazon.user.infrastructure.output.jpa.repository.IUserRepository;

import com.emazon.user.infrastructure.output.security.jwt.JwtAdapter;
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

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final UserEntityMapper userEntityMapper;
    private final JwtAdapter jwtAdapter;

    @Bean
    public IAuthenticationPersistencePort authenticationPersistencePort(){
        return new AuthenticationJpaAdapter(userRepository,roleRepository,encoder(),jwtAdapter,userEntityMapper);
    }
    @Bean
    public IRolePersistencePort rolePersistencePort(){
        return new RoleJpaAdapter(roleRepository);
    }

    @Bean
    public IUserPersistencePort userPersistencePort(){
        return new UserJpaAdapter(userRepository);
    }

    @Bean
    public IAuthenticationServicePort authenticationServicePort(){
        return new AuthenticationUseCase(authenticationPersistencePort(), rolePersistencePort(),userPersistencePort() );
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(Constants.EXCEPTION_USER_NOT_FOUND));
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
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
