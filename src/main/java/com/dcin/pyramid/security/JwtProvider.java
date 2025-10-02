package com.dcin.pyramid.security;

import com.dcin.pyramid.model.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken (String email, Role role){
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role.name()) //claims.put?gpt
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public Jws<Claims> validateToken(String token){
        return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
    }

    public String getEmailFromToken(String token){
        return validateToken(token).getBody().getSubject();
    }
    public String getRoleFromToken(String token){
        return validateToken(token).getBody().get("role", String.class);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
