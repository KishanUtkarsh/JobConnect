package com.jobconnect.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtil {

    private JwtUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static final String SECRET_KEY = "EAD925F94CA15B497A4D7521C5ABE";
    private static final long ACCESS_TOKEN_EXPIRATION = 7L * 24 * 60 * 60 * 1000;  // 7 days
    private static final long REFRESH_TOKEN_EXPIRATION = 14L * 24 * 60 * 60 * 1000; // 14 days

    public static String generateJwtToken(String email, String role, String type) {
        return JWT.create()
                .withIssuer("JobConnect")
                .withSubject(email)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(type.equals("new") ? System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION : System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .sign(getAlgorithm());
    }

    public static DecodedJWT verifyToken(String token) {
        try {
            return JWT.require(getAlgorithm())
                    .withIssuer("JobConnect")
                    .build()
                    .verify(token);
        } catch (Exception e) {
            log.error("Token verification failed: {}", e.getMessage());
            return null;
        }
    }

    public static String getEmailFromToken(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getSubject();
    }

    public static String getRoleFromToken(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getClaim("role").asString();
    }

    public static boolean isTokenExpired(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getExpiresAt().before(new Date());
    }

    public static Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SECRET_KEY);
    }
}
