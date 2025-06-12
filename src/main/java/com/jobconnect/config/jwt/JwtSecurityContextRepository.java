package com.jobconnect.config.jwt;

import com.jobconnect.common.exception.InvalidJwtTokenException;
import com.jobconnect.common.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {

    private final JwtAuthenticationManager jwtAuthenticationManager;
    public JwtSecurityContextRepository(JwtAuthenticationManager jwtAuthenticationManager) {
        this.jwtAuthenticationManager = jwtAuthenticationManager;
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {

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

        Authentication auth = new UsernamePasswordAuthenticationToken(token, token);

        return jwtAuthenticationManager
                .authenticate(auth)
                .map(SecurityContextImpl::new);

    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }
}