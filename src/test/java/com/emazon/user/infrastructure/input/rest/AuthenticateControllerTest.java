package com.emazon.user.infrastructure.input.rest;

import com.emazon.user.application.dto.AuthenticationDtoResponse;
import com.emazon.user.application.dto.LoginDto;
import com.emazon.user.application.handler.IAuthenticationHandler;
import com.emazon.user.configuration.TestSecurityConfig;
import com.emazon.user.infrastructure.output.security.jwt.JwtTokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = AuthenticateRestController.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {AuthenticateRestController.class, TestSecurityConfig.class})
class AuthenticateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IAuthenticationHandler authenticationHandler;

    @MockBean
    private JwtTokenManager jwtTokenManager;

    @Test
    void AuthenticateRestController_Login_ShouldReturnToken() throws Exception {
        LoginDto loginDto = new LoginDto(
                "julian.ortixs@gmail.com",
                "abcderthf");

        AuthenticationDtoResponse authenticationDtoResponse =
                new AuthenticationDtoResponse("valid-token");

        Mockito.when(authenticationHandler.login(Mockito.any(LoginDto.class)))
                .thenReturn(authenticationDtoResponse);

        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(authenticationDtoResponse)));
    }

    @Test
    void AuthenticateRestController_Login_WhenPasswordIsNull_SholReturnBadRequest() throws Exception {
        LoginDto loginDto = new LoginDto(
                "julian.ortixs@gmail.com",
                null);

        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void AuthenticateRestController_Login_WhenMailIsNull_SholReturnBadRequest() throws Exception {
        LoginDto loginDto = new LoginDto(
                null,
                "password123");

        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}