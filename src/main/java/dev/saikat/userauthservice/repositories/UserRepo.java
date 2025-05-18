package dev.saikat.userauthservice.repositories;

import dev.saikat.userauthservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmailEquals(String email);
}
