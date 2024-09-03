package com.emazon.user.infrastructure.input.rest;

import com.emazon.user.application.dto.AuthenticationDtoResponse;
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
    })
    @PostMapping("/register/aux-bodega")
    public ResponseEntity<AuthenticationDtoResponse> register(@Valid @RequestBody RegisterDtoRequest registerRequest) {
        return ResponseEntity.ok(authenticationHandler.registerWareHouseAssistan(registerRequest));
    }
}
