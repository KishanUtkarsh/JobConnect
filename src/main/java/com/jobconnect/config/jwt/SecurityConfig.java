package com.jobconnect.config.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private final JwtSecurityContextRepository contextRepository;

    public SecurityConfig(JwtSecurityContextRepository contextRepository) {
        this.contextRepository = contextRepository;
    }

    private static final String[] WHITELIST_URLS = {
            // Public API endpoints
            "/api/public/**",

            // Swagger UI and OpenAPI documentation
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**"
    };
    private static final String[] AUTH_URLS = {
            "/api/public/auth/**",
    };

    private static final String[] ADMIN_URLS = {
            "/api/v1/users/**",
    };

    private static final String[] JOBSEEKER_URLS = {
            "/api/v1/jobApplication-jobseeker/**",
            "/api/v1/jobseeker/**",

    };

    private static final String[] RECRUITER_URLS = {
            "/api/v1/job/**",
            "/api/v1/jobApplication-recruiter/**",
            "/api/v1/recruiter/**"
    };



    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtAuthenticationManager authenticationManager) {
        return http
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.POST, AUTH_URLS).permitAll()
                        .pathMatchers(WHITELIST_URLS).permitAll()
                        .pathMatchers(ADMIN_URLS).hasAnyRole("ADMIN")
                        .pathMatchers(JOBSEEKER_URLS).hasRole("JOBSEEKER")
                        .pathMatchers(RECRUITER_URLS).hasRole("RECRUITER")
                        .anyExchange().denyAll()
                )
                .authenticationManager(authenticationManager)
                .securityContextRepository(contextRepository)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}