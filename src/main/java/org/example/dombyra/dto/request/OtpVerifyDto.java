package org.example.dombyra.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record OtpVerifyDto(
        @Schema(example = "+77001234567")
        @NotBlank
        @Pattern(regexp = "^\\+77\\d{9}$")
        String phoneNumber,

        @Schema(example = "1234")
        @NotBlank
        String otp
) {}
