package com.demo.taskproject.security;

import com.demo.taskproject.exception.APIExecption;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication){
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime()+3600000);
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,"JWTSecretKey")
                .compact();
        return token;

    }
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser().
                setSigningKey("JWTSigningKey")
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
        public boolean validateToken(String token){
            try {
                Jwts.parser().setSigningKey("JWTSecertkey")
                        .build()
                        .parseClaimsJws(token);
                return true;
            }
           catch(Exception e){
                throw  new APIExecption("Token issue: "+ e.getMessage());}

        }
    }

