package org.example.dombyra.dto;

public record LoginResponse(String accessToken, String refreshToken,UserInfoResponse message) {
    public LoginResponse(String errorMessage){
        this(null,null,new UserInfoResponse(errorMessage,null));
    }
}
