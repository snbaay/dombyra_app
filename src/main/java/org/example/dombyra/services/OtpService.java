package org.example.dombyra.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dombyra.dto.LoginResponse;
import org.example.dombyra.dto.OtpVerifyDto;
import org.example.dombyra.dto.RegisterRequest;
import org.example.dombyra.dto.TempOtpData;
import org.example.dombyra.models.User;
import org.example.dombyra.models.enums.Role;
import org.example.dombyra.repositories.UserRepository;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenericResponseService responseBuilder;
    private Map<String, TempOtpData> otpStore = new ConcurrentHashMap<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    public String generateOtp(String phoneNumber, RegisterRequest registerRequest) {
        int otpValue = new Random().nextInt(10000);
        log.debug("Generated OTP value: {}", otpValue);
        String otp = String.format("%04d", otpValue);  // Используем %04d для целых чисел
        otpStore.put(phoneNumber, new TempOtpData(registerRequest, otp, System.currentTimeMillis() + 5 * 60 * 1000));
        return otp;
    }



    public ResponseEntity<LoginResponse> verifyOtp(OtpVerifyDto otpVerifyDto){
        TempOtpData otpData = otpStore.get(otpVerifyDto.phoneNumber());
        if (otpData == null){
            return ResponseEntity.badRequest().body(new LoginResponse("OTP not found"));
        }
        if (System.currentTimeMillis() > otpData.expiry()){
            otpStore.remove(otpVerifyDto.phoneNumber());
            return ResponseEntity.badRequest().body(new LoginResponse("OTP expired"));
        }
        if (otpData.otp().equals(otpVerifyDto.otp())){
            RegisterRequest restoredRequest = otpData.user();

            // 2. Создаем и маппируем DTO в Entity User
            User newUser = new User();
            newUser.setName(restoredRequest.userName());
            newUser.setPhoneNumber(restoredRequest.phoneNumber());

            // Пароль должен быть ЗАШИФРОВАН перед сохранением
            newUser.setPassword(passwordEncoder.encode(restoredRequest.password()));

            // Устанавливаем дефолтные поля
            newUser.setActive(true); // Теперь пользователь активен
            newUser.getRoles().add(Role.ROLE_USER); // Назначаем роль

            // 3. Сохраняем User в БД
            userRepository.save(newUser);
//            userRepository.save(newUser);
            otpStore.remove(otpVerifyDto.phoneNumber());
            return ResponseEntity.ok(new LoginResponse("OTP verified"));
        }
        else {
            return ResponseEntity.badRequest().body(new LoginResponse("Invalid OTP"));
        }
    }


}
