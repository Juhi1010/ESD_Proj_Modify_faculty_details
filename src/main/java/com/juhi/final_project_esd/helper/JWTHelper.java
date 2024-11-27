package com.juhi.final_project_esd.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTHelper {
    private String SECRET_KEY = "cr666N7wIV+KJ2xOQpWtcfAekL4YXd9gbnJMs8SJ9sI=";

    // Extract username from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractEmployeeId(String token) {
        return extractClaim(token, claims -> claims.get("employee_id", Long.class));
    }

    // Extract expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract claims
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // Check if token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Generate token
    public String generateToken(String username, Long employeeId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("employee_id", employeeId);
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Token valid for 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }


    // Validate token
    public Boolean validateToken(String token) {
        final String extractedUsername = extractUsername(token);
        return !isTokenExpired(token);
    }

    public String removeBearerFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return token;
    }
}
