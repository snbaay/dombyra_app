package org.example.dombyra.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.request.LoginPhoneRequest;
import org.example.dombyra.dto.request.LoginRequest;
import org.example.dombyra.dto.response.LoginResponse;
import org.example.dombyra.dto.request.RefreshRequest;
import org.example.dombyra.services.AuthService;
import org.example.dombyra.services.RefreshAccessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshAccessService refreshAccessService;

    // ДОБАВЛЯЕМ @Valid и меняем Map на LoginOtpRequest
    @Operation(summary = "Login Step 1: Request OTP for existing user")
    @PostMapping("/login/request")
    public ResponseEntity<?> requestLoginOtp(@Valid @RequestBody LoginPhoneRequest request) {
        return authService.requestLoginOtp(request.phoneNumber());
    }

    // ИЗМЕНЕНО ОПИСАНИЕ: Это теперь Шаг 2 для входа
    @Operation(summary = "Login Step 2: Verify OTP and get tokens")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

    @Operation(summary = "Refresh access token using refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@Valid @RequestBody RefreshRequest refreshRequest){
        return refreshAccessService.refreshAccessToken(refreshRequest);
    }
}