package com.jobconnect.config.jwt;

import com.jobconnect.common.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        String token = authentication.getCredentials().toString();
        log.debug("Extracted token: {}", token);

        String uuid = JwtUtil.getUuidFromToken(token);
        String role = JwtUtil.getRoleFromToken(token);

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        Authentication auth = new UsernamePasswordAuthenticationToken(uuid, null, authorities);

        return Mono.just(auth);
    }
}
