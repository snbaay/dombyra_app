package org.example.dombyra.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.LoginResponse;
import org.example.dombyra.dto.OtpVerifyDto;
import org.example.dombyra.dto.RegisterRequest;
import org.example.dombyra.models.User;
import org.example.dombyra.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final OtpService otpService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ResponseEntity<?> registerUser(RegisterRequest registerRequest){
        Optional<User> existing = userRepository.findByPhoneNumber(registerRequest.phoneNumber());
        if (existing.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error","User already exists"));
        }
        if (registerRequest.phoneNumber() == null || registerRequest.password().length() < 8){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","Password too short"));
        }
        String otp = otpService.generateOtp(registerRequest.phoneNumber(),registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("otp",otp));
    }

//    public ResponseEntity<LoginResponse> verifyOtp(OtpVerifyDto otpVerifyDto){
//        return otpService.verifyOtp(otpVerifyDto);
//    }
}
