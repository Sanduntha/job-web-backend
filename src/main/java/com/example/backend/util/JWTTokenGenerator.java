package com.example.backend.util;

import com.example.backend.entity.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTTokenGenerator {


    //Key eka genarate karanawa
    SecretKey key = Jwts.SIG.HS256.key().build();

    //Token eka genarate karala time ekak denawa
    public String generateToken(User user) {
        long EXPIRATION_TIME = 1000 * 60 * 60;

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }



    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (JwtException e) {
            System.out.println("Invalid JWT: " + e.getMessage());
            return false;
        }
    }
}