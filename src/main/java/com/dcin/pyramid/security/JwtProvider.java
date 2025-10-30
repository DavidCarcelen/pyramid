package com.dcin.pyramid.security;

import com.dcin.pyramid.model.entity.Player;
import com.dcin.pyramid.model.entity.Store;
import com.dcin.pyramid.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(User user) {
        String userType;

        if (user instanceof Player) {
            userType = "PLAYER";
        } else if (user instanceof Store) {
            userType = "STORE";
        } else {
            throw new IllegalArgumentException("Unknown user type: " + user.getClass());
        }

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userType", userType)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }

    public String getEmailFromToken(String token) {
        return validateToken(token).getBody().getSubject();
    }

    public String getUserTypeFromToken(String token) {
        return validateToken(token).getBody().get("userType", String.class);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

