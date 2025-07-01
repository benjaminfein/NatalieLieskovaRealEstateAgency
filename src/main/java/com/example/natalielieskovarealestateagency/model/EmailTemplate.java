package com.example.natalielieskovarealestateagency.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "email_templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_key", nullable = false)
    private String templateKey;

    @Column(name = "language", nullable = false, unique = false)
    private String language;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "body", columnDefinition = "TEXT", nullable = false)
    private String body;
}