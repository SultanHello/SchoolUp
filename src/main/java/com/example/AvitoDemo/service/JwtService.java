package com.example.AvitoDemo.service;

import com.example.AvitoDemo.Model.Student;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public class JwtService {
    private final String SECRET_KEY="4b4d109b8a545947e3d06e7856aad9a97f546e19330280128170df02bbe3e211";
    public String generateToken(Student user) {
        String token = Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*24*1000))
                .signWith(getSignKey())
                .compact();
        return token;


    }
    public SecretKey getSignKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String extractEmail(String token){
        return extractClaim(token,Claims::getSubject);

    }
    public <T>T extractClaim(String token, Function<Claims,T> resolver){
        Claims claims =extractAllClaims(token);

        return resolver.apply(claims);

    }
    public Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
    }
    public boolean isValid(String token, UserDetails user){
        String email = extractEmail(token);
        return email.equals(user.getUsername())&&! isValidExtract(token);

    }
    public boolean isValidExtract(String token){
        return extractExpiration(token).before(new Date());

    }

    public Date extractExpiration(String token){

        return extractClaim(token,Claims::getExpiration);

    }



}
