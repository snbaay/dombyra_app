package org.example.dombyra.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.response.UserInfoResponse;
import org.example.dombyra.dto.response.UserResponse;
import org.example.dombyra.dto.request.UserUpdateRequest;
import org.example.dombyra.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getUser(Authentication authentication){
        return ResponseEntity.ok(userService.getUser(authentication.getName()));
    }

    @PatchMapping()
    public ResponseEntity<UserInfoResponse> updateUser(Authentication authentication, @RequestBody UserUpdateRequest userUpdateRequest){
        return ResponseEntity.ok(userService.updateUser(authentication.getName(), userUpdateRequest));
    }

    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file, Authentication authentication) {
        userService.uploadPhoto(file, authentication);
        return ResponseEntity.ok(Map.of("message", "Photo uploaded"));
    }
}
