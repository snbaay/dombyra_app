package org.example.dombyra.dto;

public record TempOtpData(RegisterRequest user, String otp, long expiry) {
}
