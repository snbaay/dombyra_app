package org.example.dombyra.services;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dombyra.dto.response.CloudinaryResponse;
import org.example.dombyra.dto.response.UserInfoResponse;
import org.example.dombyra.dto.response.UserResponse;
import org.example.dombyra.dto.request.UserUpdateRequest;
import org.example.dombyra.models.User;
import org.example.dombyra.repositories.UserRepository;
import org.example.dombyra.util.FileUploadUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CloudinaryService cloudinaryService;
    private final Cloudinary cloudinary;

    public UserResponse getUser(String phoneNumber){
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getPhoneNumber(),
                user.getPhotoUrl()
        );
    }

    public UserInfoResponse updateUser(String phoneNumber, UserUpdateRequest userUpdateRequest){
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userUpdateRequest.name());
        user.setPhoneNumber(userUpdateRequest.phoneNumber());
        if (userUpdateRequest.name() != null && !userUpdateRequest.name().trim().isEmpty()) {
            user.setName(userUpdateRequest.name());
        }

        if (userUpdateRequest.phoneNumber() != null && !userUpdateRequest.phoneNumber().trim().isEmpty()) {
            user.setPhoneNumber(userUpdateRequest.phoneNumber());
        }
        userRepository.save(user);
        return new UserInfoResponse(user.getName(),user.getPhoneNumber());
    }

    public void uploadPhoto(MultipartFile file, Authentication authentication) {
        FileUploadUtil.assertAllowed(file, FileUploadUtil.IMAGE_PATTERN.pattern());

        User user = userRepository.findByPhoneNumber(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getPhotoPublicId() != null) {
            try {
                cloudinary.uploader().destroy(user.getPhotoPublicId(), Map.of());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String filename = FileUploadUtil.getFileName(authentication.getName());
        CloudinaryResponse response = cloudinaryService.uploadFile(file, filename);
        user.setPhotoUrl(response.url());
        user.setPhotoPublicId(response.publicId());
        userRepository.save(user);
    }
}
