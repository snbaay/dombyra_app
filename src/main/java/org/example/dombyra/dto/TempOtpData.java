package org.example.dombyra.dto;

import org.example.dombyra.dto.request.RegisterRequest;

public record TempOtpData(RegisterRequest user, String otp, long expiry) {
}

