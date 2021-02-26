package com.poliakov.springbootconference.repository;

import com.poliakov.springbootconference.model.User;
import com.poliakov.springbootconference.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(UserRole role);
}
