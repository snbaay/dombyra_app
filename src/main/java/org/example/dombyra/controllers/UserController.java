package org.example.dombyra.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.UserInfoResponse;
import org.example.dombyra.dto.UserResponse;
import org.example.dombyra.dto.UserUpdateRequest;
import org.example.dombyra.models.User;
import org.example.dombyra.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    @GetMapping("{id}")
//    public UserInfoResponse getUserById(@PathVariable Long id){
//        return userService.getUserById(id);
//    }

    @GetMapping
    public ResponseEntity<UserResponse> getUser(Authentication authentication){
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return ResponseEntity.status(401).build();
//        }
        return ResponseEntity.ok(userService.getUser(authentication.getName()));
    }

    @PutMapping("{id}")
    public UserInfoResponse updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest){
        return userService.updateUser(id,userUpdateRequest);
    }
}
