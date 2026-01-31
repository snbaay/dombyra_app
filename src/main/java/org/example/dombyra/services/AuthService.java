package org.example.dombyra.services;

import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.LoginRequest;
import org.example.dombyra.dto.LoginResponse;
import org.example.dombyra.dto.RefreshRequest;
import org.example.dombyra.dto.UserInfoResponse;
import org.example.dombyra.models.User;
import org.example.dombyra.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest){
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.phoneNumber(),loginRequest.password()));
            User user = userRepository.findByPhoneNumber(loginRequest.phoneNumber()).orElseThrow(() -> new RuntimeException("User not found"));
            UserInfoResponse userInfoResponse = new UserInfoResponse(user.getName(),user.getPhoneNumber());
            String accessToken = jwtService.generateAccessToken(user.getPhoneNumber());
            String refreshToken = jwtService.generateRefreshToken(user.getPhoneNumber());
            LoginResponse loginResponse = new LoginResponse(accessToken,refreshToken,userInfoResponse);
            return ResponseEntity.ok(loginResponse);
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("Invalid phone number or password"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new LoginResponse(e.getMessage()));
        }
    }



}
