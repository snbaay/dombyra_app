package org.example.dombyra.services;

import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.request.OtpVerifyDto;
import org.example.dombyra.dto.TempOtpData;
import org.example.dombyra.dto.request.RegisterRequest;
import org.example.dombyra.dto.response.OtpResponse;
import org.example.dombyra.models.User;
import org.example.dombyra.models.enums.Role;
import org.example.dombyra.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegisterService {

    private final UserRepository userRepository;
    private final OtpService otpService;
    private final JwtService jwtService; // Добавили генератор токенов сюда

    // Шаг 1: Запрос кода
    public ResponseEntity<?> registerUser(RegisterRequest registerRequest){
        Optional<User> existing = userRepository.findByPhoneNumber(registerRequest.phoneNumber());
        if (existing.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error","User already exists"));
        }
        String otp = otpService.generateOtp(registerRequest.phoneNumber(), registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("otp", otp));
    }

    // Шаг 2: Проверка кода и создание юзера
    public ResponseEntity<OtpResponse> verifyAndSaveUser(OtpVerifyDto dto) {
        // Просим OtpService проверить код
        TempOtpData validData = otpService.validateAndGetOtpData(dto.phoneNumber(), dto.otp());

        if (validData == null || validData.user() == null) {
            return ResponseEntity.badRequest().body(new OtpResponse("Invalid or expired OTP"));
        }

        // Берем данные, которые юзер вводил на Шаге 1
        RegisterRequest request = validData.user();

        // Сохраняем в БД (Neon)
        User newUser = new User();
        newUser.setName(request.userName());
        newUser.setPhoneNumber(request.phoneNumber());
        newUser.setActive(true);
        newUser.getRoles().add(Role.ROLE_USER);
        userRepository.save(newUser);

        // Выдаем токены
        String accessToken = jwtService.generateAccessToken(newUser.getPhoneNumber());
        String refreshToken = jwtService.generateRefreshToken(newUser.getPhoneNumber());

        return ResponseEntity.ok(new OtpResponse("Registration complete!", accessToken, refreshToken));
    }
}