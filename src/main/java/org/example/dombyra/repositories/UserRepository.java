package org.example.dombyra.repositories;

import org.example.dombyra.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberIgnoreCase(String phoneNumber);
    Optional<User> findByPhoneNumberIgnoreCase(String phoneNumber);

}
