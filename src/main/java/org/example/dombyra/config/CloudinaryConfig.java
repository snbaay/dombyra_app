package org.example.dombyra.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        final Map<String, String> config = new HashMap<>();
        config.put("cloud_name","dieixggfz");
        config.put("api_key","112577243939464");
        config.put("api_secret","N-q5G6X9guV97RzTpo8B-tMOFrI");
        return new Cloudinary(config);
    }
}
