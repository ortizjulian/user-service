package com.emazon.user.infrastructure.input.rest;

import com.emazon.user.application.dto.AuthenticationDtoResponse;
import com.emazon.user.application.dto.LoginDto;
import com.emazon.user.application.dto.RegisterDtoRequest;
import com.emazon.user.application.handler.IAuthenticationHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Validated
public class AuthenticateController {
    private final IAuthenticationHandler authenticationHandler;

    @Operation(
            summary = "Register a new warehouse assistant",
            description = "This endpoint allows the registration of a new warehouse assistant."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered"),
            @ApiResponse(responseCode = "404", description = "Brand or category not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request, check the submitted data"),
    })
    @PostMapping("/register/aux-bodega")
    public ResponseEntity<AuthenticationDtoResponse> register(@Valid @RequestBody RegisterDtoRequest registerRequest) {
        return ResponseEntity.ok(authenticationHandler.registerWareHouseAssistan(registerRequest));
    }

    @Operation(
            summary = "Login",
            description = "This endpoint allows to login."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged in successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request, check the submitted data"),
            @ApiResponse(responseCode = "401", description = "Invalid Credentials"),
    })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationDtoResponse> login(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authenticationHandler.login(loginDto));
    }
}
