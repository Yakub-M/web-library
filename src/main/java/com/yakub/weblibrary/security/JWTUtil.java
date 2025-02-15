package com.yakub.weblibrary.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JWTUtil {
    private final String jwtSecret = "YourSecretKey"; // Set this as an environment variable in prod. Like set an JWT_SECRET in Docker.
    private final long jwtExpirationMs = 86400000; // 24 hours

    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                   .setSubject(userPrincipal.getUsername())
                   .claim("roles", userPrincipal.getAuthorities())
                   .setIssuedAt(new Date())
                   .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                   .signWith(SignatureAlgorithm.HS512, jwtSecret)
                   .compact();
    }

    public String getUsernameFromJwt(String token) {
        return Jwts.parser()
                   .setSigningKey(jwtSecret)
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }

    public boolean validateJwt(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Log the error if needed
        }
        return false;
    }
}

