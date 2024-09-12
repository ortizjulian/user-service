package com.emazon.user.infrastructure.input.rest;

import com.emazon.user.application.dto.RegisterDtoRequest;
import com.emazon.user.application.handler.IAuthenticationHandler;
import com.emazon.user.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final IUserHandler userHandler;

    private final IAuthenticationHandler authenticationHandler;


    @Operation(
            summary = "Register a new warehouse assistant",
            description = "This endpoint allows the registration of a new warehouse assistant."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered"),

            @ApiResponse(responseCode = "404", description = "Role not found"),

            @ApiResponse(responseCode = "400", description = "Invalid request, check the submitted data"),
    })
    @PostMapping("/register/aux-bodega")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterDtoRequest registerRequest) {
        userHandler.registerWareHouseAssistan(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Register a new client",
            description = "This endpoint allows the registration of a new client."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered"),
            @ApiResponse(responseCode = "404", description = "Role not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request, check the submitted data"),
    })
    @PostMapping("/register/cliente")
    public ResponseEntity<Void> registerClient(@Valid @RequestBody RegisterDtoRequest registerRequest) {
        userHandler.registerClient(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
