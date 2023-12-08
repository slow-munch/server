package com.rumor.slowmunchserver.utils;

import com.rumor.slowmunchserver.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Jwt {

    private final String secretKey;

    public Jwt(@Value("${jwt.secretKey}") String secretKey) {
        this.secretKey = secretKey;
    }

    public String createToken(User user) {
        Claims claims = Jwts.claims()
                .setSubject(user.getSubject());
        claims.put("email", user.getEmail());

        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 3600000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Object getClaims(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claimsJws.getBody().getExpiration().before(new Date())) {
                throw new RuntimeException("인증 실패");
            }

            return claimsJws.getBody();
        } catch (Exception e) {
            return false;
        }
    }
}
