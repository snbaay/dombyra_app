package org.example.dombyra.dto.response;

public record UserInfoResponse(String name,String phoneNumber) {
    public UserInfoResponse(String errorMessage){
        this(errorMessage,"");
    }
}
