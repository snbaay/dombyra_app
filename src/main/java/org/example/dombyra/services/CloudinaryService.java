package org.example.dombyra.services;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.response.CloudinaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private Cloudinary cloudinary;
    @Transactional
    public CloudinaryResponse uploadFile(MultipartFile file,String filename){
        try {
            final Map result = cloudinary.uploader().upload(file.getBytes(), Map.of("public_id","users/avatar/" + filename));
            final String url = (String) result.get("secure_url");
            final String publicId = (String) result.get("public_id");
            return CloudinaryResponse.builder().publicId(publicId).url(url).build();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to upload file" + e.getMessage());
        }
    }
}
