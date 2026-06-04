package org.example.dombyra.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Номер телефона обязателен")
        @Pattern(regexp = "^\\+77\\d{9}$", message = "Неверный формат номера. Ожидается: +77XXXXXXXXX")
        String phoneNumber,
         @NotBlank
         @Size(min = 4, max = 4)
                 String otp
) {}