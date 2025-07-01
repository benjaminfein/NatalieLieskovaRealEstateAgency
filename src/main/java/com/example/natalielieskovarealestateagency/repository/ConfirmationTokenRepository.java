package com.example.natalielieskovarealestateagency.repository;

import com.example.natalielieskovarealestateagency.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);

    void deleteByUserId(Long userId);
}
