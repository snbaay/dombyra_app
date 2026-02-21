package org.example.dombyra.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.request.LoginRequest;
import org.example.dombyra.dto.response.LoginResponse;
import org.example.dombyra.dto.request.RefreshRequest;
import org.example.dombyra.services.AuthService;
import org.example.dombyra.services.RefreshAccessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshAccessService refreshAccessService;

    @Operation(summary = "Login with phone number and password")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

    @Operation(summary = "Refresh access token using refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@Valid @RequestBody RefreshRequest refreshRequest){
        return refreshAccessService.refreshAccessToken(refreshRequest);
    }
}