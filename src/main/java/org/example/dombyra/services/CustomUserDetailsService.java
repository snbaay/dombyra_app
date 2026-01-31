package org.example.dombyra.services;

import lombok.RequiredArgsConstructor;
import org.example.dombyra.models.User;
import org.example.dombyra.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if (userOptional == null ){
            throw new UsernameNotFoundException("User with this Phone number not found");
        }
        User user = userOptional.get();
        return user;
    }
}
