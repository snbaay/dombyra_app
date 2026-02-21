package org.example.dombyra.dto.response;

public record OtpResponse(String message,String accessToken,String refreshToken) {
    public OtpResponse(String errorMessage){
        this("",null,null);
    }
}
