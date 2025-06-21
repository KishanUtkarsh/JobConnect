package com.jobconnect.config.jwt;

import com.jobconnect.common.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@Primary
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        String token = authentication.getCredentials().toString();
        log.debug("Extracted token: {}", token);

        String uuid = JwtUtil.getUuidFromToken(token);
        String role = JwtUtil.getRoleFromToken(token);
        UUID userId = UUID.fromString(uuid);

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        Authentication auth = new UsernamePasswordAuthenticationToken(userId, null, authorities);

        return Mono.just(auth);
    }
}
