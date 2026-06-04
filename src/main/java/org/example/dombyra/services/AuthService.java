package org.example.dombyra.services;

import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.TempOtpData;
import org.example.dombyra.dto.request.LoginRequest;
import org.example.dombyra.dto.response.LoginResponse;
import org.example.dombyra.dto.response.UserInfoResponse;
import org.example.dombyra.models.User;
import org.example.dombyra.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final OtpService otpService;

    // Шаг 1: Запрос кода для логина
    public ResponseEntity<?> requestLoginOtp(String phoneNumber) {
        Optional<User> existingUser = userRepository.findByPhoneNumber(phoneNumber);
        if (existingUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found. Please register first."));
        }

        String otp = otpService.generateOtpForLogin(phoneNumber);
        return ResponseEntity.ok(Map.of("message", "OTP sent successfully", "otp", otp));
    }

    // Шаг 2: Проверка кода и выдача токенов
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest){
        try {
            // ИСПРАВЛЕНО: Вызываем правильный метод из нового OtpService
            TempOtpData validData = otpService.validateAndGetOtpData(loginRequest.phoneNumber(), loginRequest.otp());

            if (validData == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponse("Invalid or expired OTP"));
            }

            // Код верный! Достаем юзера из базы
            User user = userRepository.findByPhoneNumber(loginRequest.phoneNumber())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Генерируем токены
            UserInfoResponse userInfoResponse = new UserInfoResponse(user.getName(), user.getPhoneNumber());
            String accessToken = jwtService.generateAccessToken(user.getPhoneNumber());
            String refreshToken = jwtService.generateRefreshToken(user.getPhoneNumber());

            LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken, userInfoResponse);
            return ResponseEntity.ok(loginResponse);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new LoginResponse(e.getMessage()));
        }
    }
}