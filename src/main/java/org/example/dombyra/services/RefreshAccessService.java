package org.example.dombyra.services;

import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.RefreshRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RefreshAccessService {
    private JwtService jwtService;

    public ResponseEntity<?> refreshAccessToken(RefreshRequest refreshRequest){
        String refreshToken = refreshRequest.refreshToken();
        try {
            String phoneNumber;
            try {
                phoneNumber = jwtService.extractUserPhoneNumber(refreshToken);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid token format"));
            }
            boolean isValid = jwtService.isRefreshTokenValid(refreshToken,phoneNumber);

            if (!isValid) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid refresh token"));
            }
            String newAccessToken = jwtService.generateAccessToken(phoneNumber);
            return ResponseEntity.ok(Map.of("accessToken",newAccessToken));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid token"));
        }
    }
}
