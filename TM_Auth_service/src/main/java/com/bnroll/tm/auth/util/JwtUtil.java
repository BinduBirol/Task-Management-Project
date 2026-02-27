package com.bnroll.tm.auth.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.google.api.client.util.Value;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    @PostConstruct
    public void debug() {
        System.out.println("JWT SECRET = " + secret);
    }

    public String generateToken(String email) {
    	return Jwts.builder()
    	        .setSubject(email)
    	        .setIssuedAt(new Date())
    	        .setExpiration(new Date(System.currentTimeMillis() + expiration))
    	        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
    	        .compact();
    }
}