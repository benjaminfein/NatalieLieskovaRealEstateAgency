package com.example.natalielieskovarealestateagency.repository;

import com.example.natalielieskovarealestateagency.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRole(String role);

    List<User> findByEmailConfirmedFalseAndDateOfCreatedBefore(Date date);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}