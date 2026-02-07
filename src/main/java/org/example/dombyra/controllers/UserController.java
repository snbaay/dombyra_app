package org.example.dombyra.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.UserInfoResponse;
import org.example.dombyra.dto.UserUpdateRequest;
import org.example.dombyra.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    public UserInfoResponse getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("{id}")
    public UserInfoResponse updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest){
        return userService.updateUser(id,userUpdateRequest);
    }
}
