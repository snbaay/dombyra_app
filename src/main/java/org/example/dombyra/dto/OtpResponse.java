package org.example.dombyra.dto;

public record OtpResponse(String message,String accessToken,String refreshToken) {
    public OtpResponse(String errorMessage){
        this("",null,null);
    }
}
