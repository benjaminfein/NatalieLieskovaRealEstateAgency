package com.example.natalielieskovarealestateagency.service.impl;

import com.example.natalielieskovarealestateagency.model.EmailTemplate;
import com.example.natalielieskovarealestateagency.repository.EmailTemplateRepository;
import com.example.natalielieskovarealestateagency.service.EmailTemplateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService {
    private final EmailTemplateRepository emailTemplateRepository;

    @Override
    public EmailTemplate createTemplate(EmailTemplate template) {
        return emailTemplateRepository.save(template);
    }

    @Override
    public String getTemplateBody(String templateKey, String language) {
        Optional<EmailTemplate> template = emailTemplateRepository.findByTemplateKeyAndLanguage(templateKey, language);
        return template.map(EmailTemplate::getBody)
                .orElseThrow(() -> new RuntimeException("Шаблон " + templateKey + " не найден"));
    }

    @Override
    public String getTemplateSubject(String templateKey, String language) {
        Optional<EmailTemplate> template = emailTemplateRepository.findByTemplateKeyAndLanguage(templateKey, language);
        return template.map(EmailTemplate::getSubject)
                .orElseThrow(() -> new RuntimeException("Шаблон " + templateKey + " не найден"));
    }

    @Override
    public EmailTemplate getTemplateByKeyAndLanguage(String templateKey, String language) {
        return emailTemplateRepository.findByTemplateKeyAndLanguage(templateKey, language)
                .orElseThrow(() -> new RuntimeException("Шаблон " + templateKey + " с языком " + language + " не найден."));
    }

    @Override
    public List<EmailTemplate> getAllTemplates() {
        return emailTemplateRepository.findAll();
    }

    @Override
    public EmailTemplate updateTemplate(Long id, EmailTemplate updatedTemplate) {
        EmailTemplate existingTemplate = emailTemplateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Шаблон не найден"));

        existingTemplate.setTemplateKey(updatedTemplate.getTemplateKey());
        existingTemplate.setSubject(updatedTemplate.getSubject());
        existingTemplate.setBody(updatedTemplate.getBody());

        return emailTemplateRepository.save(existingTemplate);
    }

    @Override
    public void deleteTemplate(Long id) {
        emailTemplateRepository.deleteById(id);
    }
}