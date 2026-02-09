package org.example.dombyra.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dombyra.dto.UserInfoResponse;
import org.example.dombyra.dto.UserUpdateRequest;
import org.example.dombyra.models.User;
import org.example.dombyra.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
//    public UserInfoResponse getUserById(Long id){
//        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
//        return new UserInfoResponse(user.getName(),user.getPhoneNumber());
//    }
    public User getUser(Authentication auth){
        String phoneNumber = auth.getName();
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new RuntimeException("User not found"));
    }


    public UserInfoResponse updateUser(Long id, UserUpdateRequest userUpdateRequest){
        User user = userRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        user.setName(userUpdateRequest.name());
        user.setPhoneNumber(userUpdateRequest.phoneNumber());
        userRepository.save(user);
        return new UserInfoResponse(user.getName(),user.getPhoneNumber());
    }
}
