package org.example.dombyra.dto;

public record UserInfoResponse(String name,String phoneNumber) {
    public UserInfoResponse(String errorMessage){
        this(errorMessage,"");
    }
}
