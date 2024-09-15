package com.emazon.user.infrastructure.input.rest;

import com.emazon.user.application.dto.RegisterDtoRequest;
import com.emazon.user.application.handler.IUserHandler;
import com.emazon.user.configuration.TestSecurityConfig;
import com.emazon.user.domain.model.Role;
import com.emazon.user.domain.model.User;
import com.emazon.user.infrastructure.output.security.jwt.JwtTokenManager;
import com.emazon.user.utils.SecurityConstants;
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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = UserRestController.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {UserRestController.class, TestSecurityConfig.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IUserHandler userHandler;

    @MockBean
    private JwtTokenManager jwtTokenManager;

    @Test
    void UserRestController_RegisterWarehouseAssistant_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        RegisterDtoRequest invalidRegisterRequest = new RegisterDtoRequest(
                "",
                "Ortiz",
                "password123",
                "julian@example.com",
                "12343534",
                "454353454",
                LocalDate.of(2004, 1, 1)
        );

        ResultActions response = mockMvc.perform(post("/user/register/aux-bodega")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRegisterRequest)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    void UserRestController_RegisterWarehouseAssistant_WithValidData_ShouldReturnCreatedStatus() throws Exception {
        RegisterDtoRequest validRegisterRequest = new RegisterDtoRequest(
                "Julian",
                "Ortiz",
                "password123",
                "julian@example.com",
                "12343534",
                "454353454",
                LocalDate.of(2004, 1, 1)
        );

        Mockito.when(userHandler.registerWareHouseAssistant(Mockito.any(RegisterDtoRequest.class))).thenReturn(new User(1L,  "Julian",
                "Ortiz",
                "password123",
                "julian@example.com",
                "12343534",
                "454353454",
                LocalDate.of(2004, 1, 1), new Role(1L, SecurityConstants.ROLE_WAREHOUSE_ASSISTANT,"")));

        ResultActions response = mockMvc.perform(post("/user/register/aux-bodega")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRegisterRequest)));
        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void RegisterClient_WithValidData_ShouldReturnCreatedStatus() throws Exception {
        RegisterDtoRequest validRegisterRequest = new RegisterDtoRequest(
                "Julian",
                "Ortiz",
                "password123",
                "julian@example.com",
                "12343534",
                "454353454",
                LocalDate.of(2004, 1, 1)
        );
        Mockito.when(userHandler.registerClient(Mockito.any(RegisterDtoRequest.class))).thenReturn(new User(1L,  "Julian",
                "Ortiz",
                "password123",
                "julian@example.com",
                "12343534",
                "454353454",
                LocalDate.of(2004, 1, 1), new Role(1L, SecurityConstants.ROLE_CLIENT,"")));

        ResultActions response = mockMvc.perform(post("/user/register/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRegisterRequest)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

}