package org.example.dombyra.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Login request using phone number and otp")
public record LoginRequest(@Schema(example = "7001112233") String phoneNumber,@Schema(example = "xxxx") String otp) {
}
