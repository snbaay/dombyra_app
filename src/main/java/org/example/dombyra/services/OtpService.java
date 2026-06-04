package org.example.dombyra.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dombyra.dto.TempOtpData;
import org.example.dombyra.dto.request.RegisterRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {
    private Map<String, TempOtpData> otpStore = new ConcurrentHashMap<>();

    // Генерация для регистрации (сохраняем данные юзера)
    public String generateOtp(String phoneNumber, RegisterRequest registerRequest) {
        String otp = String.format("%04d", new Random().nextInt(10000));
        otpStore.put(phoneNumber, new TempOtpData(registerRequest, otp, System.currentTimeMillis() + 5 * 60 * 1000));
        return otp;
    }

    // Генерация для входа (данные юзера не нужны, передаем null)
    public String generateOtpForLogin(String phoneNumber) {
        String otp = String.format("%04d", new Random().nextInt(10000));
        otpStore.put(phoneNumber, new TempOtpData(null, otp, System.currentTimeMillis() + 5 * 60 * 1000));
        return otp;
    }

    // Универсальная проверка кода. Если код верный, возвращаем данные (или null, если это был логин)
    public TempOtpData validateAndGetOtpData(String phoneNumber, String inputOtp) {
        TempOtpData otpData = otpStore.get(phoneNumber);
        if (otpData != null && System.currentTimeMillis() <= otpData.expiry() && otpData.otp().equals(inputOtp)) {
            otpStore.remove(phoneNumber); // Сразу удаляем использованный код
            return otpData;
        }
        return null; // Код неверный или просрочен
    }
}