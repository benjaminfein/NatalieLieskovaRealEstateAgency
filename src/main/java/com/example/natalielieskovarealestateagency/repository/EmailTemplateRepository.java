package com.example.natalielieskovarealestateagency.repository;

import com.example.natalielieskovarealestateagency.model.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {
    Optional<EmailTemplate> findByTemplateKeyAndLanguage(String templateKey, String language);
}