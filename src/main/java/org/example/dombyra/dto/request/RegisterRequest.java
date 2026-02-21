package org.example.dombyra.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank
        String userName,
        @NotBlank
        String phoneNumber,
        @NotBlank
        String password
) {
}
