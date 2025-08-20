package com.example.natalielieskovarealestateagency.config;

import com.example.natalielieskovarealestateagency.service.CustomUserDetailsService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.example.natalielieskovarealestateagency.service")
public class SecurityConfig {

    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${frontend_url}")
    private String frontendUrl;

    @Value("${security.restrict-admin-endpoints:true}")
    private boolean restrictAdminEndpoints;

    @Value("${test.value:NOT_SET}")
    private String testValue;

    @PostConstruct
    public void debugProfile() {
        System.out.println("🌍 PROFILE: test.value = " + testValue);
        System.out.println("🔐 restrictAdminEndpoints = " + restrictAdminEndpoints);
    }

    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/user/login",
            "/api/user/confirm",
            "/api/user/{id}",
            "/api/user/forgot-password",
            "/api/user/reset-password",
            "/api/residential-complex/**",
            "/api/house-and-townhouse/**",
            "/api/commercial/**",
            "/api/apartment/**",
    };

    private static final String[] ADMIN_ENDPOINTS = {
            "/api/user/profile",
    };

    private static final String[] OWNER_ENDPOINTS = {
            "/api/user/allUsers",
            "/api/user/register",
            "/api/user/delete/**",
    };

    @Bean
    @Profile("dev")
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(PUBLIC_ENDPOINTS).permitAll();

                    if (restrictAdminEndpoints) {
                        auth.requestMatchers(ADMIN_ENDPOINTS).hasAnyAuthority("admin", "owner");
                    } else {
                        auth.requestMatchers(ADMIN_ENDPOINTS).permitAll();
                    }

                    if (restrictAdminEndpoints) {
                        auth.requestMatchers(OWNER_ENDPOINTS).hasAuthority("owner");
                    } else {
                        auth.requestMatchers(OWNER_ENDPOINTS).permitAll();
                    }

                    auth.anyRequest().permitAll();
                })
                .anonymous(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    @Profile("prod")
    public SecurityFilterChain prodSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(PUBLIC_ENDPOINTS).permitAll();

                    if (restrictAdminEndpoints) {
                        auth.requestMatchers(ADMIN_ENDPOINTS).hasAnyAuthority("admin", "owner");
                    } else {
                        auth.requestMatchers(ADMIN_ENDPOINTS).permitAll();
                    }

                    if (restrictAdminEndpoints) {
                        auth.requestMatchers(OWNER_ENDPOINTS).hasAuthority("owner");
                    } else {
                        auth.requestMatchers(OWNER_ENDPOINTS).permitAll();
                    }

                    auth.anyRequest().permitAll();
                })
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        var secretKey = new SecretKeySpec(jwtSecretKey.getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(frontendUrl));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String role = jwt.getClaim("role");
            System.out.println("[SecurityConfig][JWT CONVERTER] Extracted role: " + role); // лог!

            if (role != null) {
                var authority = new SimpleGrantedAuthority(role);
                System.out.println("[SecurityConfig][JWT CONVERTER] Granted authority: " + authority); // лог!
                return List.of(authority);
            }

            System.out.println("[SecurityConfig][JWT CONVERTER] No role found in JWT");
            return List.of();
        });

        return converter;
    }
}