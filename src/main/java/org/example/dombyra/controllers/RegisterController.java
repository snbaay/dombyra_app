package org.example.dombyra.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.LoginResponse;
import org.example.dombyra.dto.OtpResponse;
import org.example.dombyra.dto.OtpVerifyDto;
import org.example.dombyra.dto.RegisterRequest;
import org.example.dombyra.models.User;
import org.example.dombyra.services.OtpService;
import org.example.dombyra.services.UserRegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User registration and OTP verification API")
@RequiredArgsConstructor
public class RegisterController {
    private final UserRegisterService userRegisterService;
    private final OtpService otpService;

    @Operation(
            summary = "Register new user (Step 1: request OTP)",
            description = "Takes fullname, phone, and password, validates input, generates OTP, and stores user temporarily until verification.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "OTP generated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request"),
                    @ApiResponse(responseCode = "409", description = "User already exists")
            }
    )
    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) throws JsonProcessingException{
        return userRegisterService.registerUser(registerRequest);
    }
    @Operation(
            summary = "Verify OTP (Step 2: complete registration)",
            description = "Verifies the OTP entered by the user. If correct and not expired, user is saved to the database and automatically logged in.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OTP verified, user saved and logged in"),
                    @ApiResponse(responseCode = "400", description = "Invalid or expired OTP")
            }
    )
    @PostMapping("/verify")
    public ResponseEntity<OtpResponse> verifyOtp(@RequestBody OtpVerifyDto otpVerifyDto) throws JsonProcessingException{
        return otpService.verifyOtp(otpVerifyDto);
    }
}
