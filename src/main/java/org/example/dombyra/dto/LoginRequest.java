package org.example.dombyra.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Login request using phone number and password")
public record LoginRequest(@Schema(example = "7001112233") String phoneNumber,@Schema(example = "mypassword") String password) {
}
