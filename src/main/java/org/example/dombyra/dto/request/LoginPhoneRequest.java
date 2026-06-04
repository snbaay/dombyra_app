package org.example.dombyra.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginPhoneRequest(
        @NotBlank
        @Pattern(regexp = "^\\+77\\d{9}$")
        String phoneNumber
) {}
