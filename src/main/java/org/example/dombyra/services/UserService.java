package org.example.dombyra.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dombyra.dto.UserInfoResponse;
import org.example.dombyra.dto.UserUpdateRequest;
import org.example.dombyra.models.User;
import org.example.dombyra.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    public UserInfoResponse getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        return new UserInfoResponse(user.getName(),user.getPhoneNumber());
    }

//    public List<UserInfoResponse> getList(){
//        return (List<UserInfoResponse>) userRepository.findAll().stream().map(user -> new UserInfoResponse(user.getName(),user.getPhoneNumber()));
//    }

    public UserInfoResponse updateUser(Long id, UserUpdateRequest userUpdateRequest){
        User user = userRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        user.setName(userUpdateRequest.name());
        user.setPhoneNumber(userUpdateRequest.phoneNumber());
        userRepository.save(user);
        return new UserInfoResponse(user.getName(),user.getPhoneNumber());
    }
}
