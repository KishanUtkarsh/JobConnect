package com.jobconnect.config;

import com.jobconnect.common.exception.InvalidJwtTokenException;
import com.jobconnect.common.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@Slf4j
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {

        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.debug("Extracted token: {}", token);
        if (token == null || !token.startsWith("Bearer ")) {
            throw  new InvalidJwtTokenException("Token is missing or does not start with 'Bearer '");
        }
        token = token.substring(7); // Remove "Bearer " prefix
        if(!JwtUtil.isValidToken(token)) {
            log.error("Invalid token is valid: {}", token);
            throw  new InvalidJwtTokenException("Invalid token");
        }
        if(JwtUtil.isTokenExpired(token)) {
            throw new InvalidJwtTokenException("Token is expired");
        }

        String email = JwtUtil.getEmailFromToken(token);
        String role = JwtUtil.getRoleFromToken(token);

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        Authentication auth = new UsernamePasswordAuthenticationToken(email, null, authorities);

        return Mono.just(auth);
    }
}
