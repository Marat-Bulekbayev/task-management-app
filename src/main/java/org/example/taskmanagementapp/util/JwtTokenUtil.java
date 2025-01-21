package org.example.taskmanagementapp.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.example.taskmanagementapp.exception.JwtTokenException;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtTokenUtil {

    private static final String SECRET_KEY = "your_secret_key";
    private static final long EXPIRATION_TIME = 300_000L;

    private JwtTokenUtil() {
    }

    public static String generateToken(String email, String role) {
        return JWT.create()
                .withSubject(email)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY.getBytes(StandardCharsets.UTF_8)));
    }

    public static boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException e) {
            throw new JwtTokenException("Invalid JWT token: " + e.getMessage());
        }
    }

    public static String getEmailFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .verify(token)
                .getSubject();
    }

    public static String getRoleFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .verify(token)
                .getClaim("role")
                .asString();
    }

    public static Date getIssuedAtFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .verify(token)
                .getIssuedAt();
    }

    public static Date getExpiresAtFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .verify(token)
                .getExpiresAt();
    }
}
