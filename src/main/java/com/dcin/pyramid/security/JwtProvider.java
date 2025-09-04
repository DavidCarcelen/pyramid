package com.dcin.pyramid.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.ES256);
    private final long EXPIRATION = 1000 * 60 * 60;

    public String generateToken (String email, String role){
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }
    public Jws<Claims> validateToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public String getEmailFromToken(String token){
        return validateToken(token).getBody().getSubject();
    }
    public String getRoleFromToken(String token){
        return validateToken(token).getBody().get("role", String.class);
    }
}
