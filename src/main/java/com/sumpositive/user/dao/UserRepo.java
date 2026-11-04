package com.sumpositive.user.dao;

import com.sumpositive.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    // Find user using email
    Optional<User> findByEmail(String email);

    // Check whether email already exists
    boolean existsByEmail(String email);
}
