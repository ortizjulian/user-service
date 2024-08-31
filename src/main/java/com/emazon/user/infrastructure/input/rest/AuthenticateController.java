package com.emazon.user.infrastructure.input.rest;

import com.emazon.user.application.dto.AuthenticationDtoResponse;
import com.emazon.user.application.dto.RegisterDtoRequest;
import com.emazon.user.application.handler.IAuthenticationHandler;
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

    @PostMapping("/register")
    public ResponseEntity<AuthenticationDtoResponse> register(@Valid @RequestBody RegisterDtoRequest registerRequest) {
        return ResponseEntity.ok(authenticationHandler.register(registerRequest));
    }
}
