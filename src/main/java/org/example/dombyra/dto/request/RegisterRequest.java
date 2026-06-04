package org.example.dombyra.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        @NotBlank(message = "Имя обязательно для заполнения")
        String userName,

        @NotBlank(message = "Номер телефона обязателен")
        // Регулярка для казахстанских номеров: начинается на +77 и дальше 9 цифр
        @Pattern(regexp = "^\\+77\\d{9}$", message = "Неверный формат номера. Ожидается: +77XXXXXXXXX")
        String phoneNumber
) {}