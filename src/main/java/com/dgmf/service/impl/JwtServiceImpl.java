package com.dgmf.service.impl;

import java.security.Key;
import java.util.function.Function;

import com.dgmf.service.JwtKeyProcessingService;
import com.dgmf.service.JwtTokenProcessingService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtTokenProcessingService, JwtKeyProcessingService {
    private static final String SECRET_KEY = "d525f722439b5a9df23de67911200fd3a4ff692dc00363630034826edb925d84"; // C

    // 4. Retrieval of "username/email" (JWT subject)
    // Before implementing this method, we need to implement
    // the process to extract Claims
    @Override
    public String getUsernameFromToken(String jwtToken) { // A
        return getClaim(jwtToken, Claims::getSubject); // E
    }

    // 4 Retrieval of "username/email" (JWT subject)
    @Override // Extract all Claims
    public Claims getAllClaims(String jwtToken) { // B
        return Jwts
                .parserBuilder()
                // Need "SigningKey" to try to generate or
                // decode a JWT Token
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    @Override
    public Key getSigningKey() { // D
        // Decode the "SECRET_KEY"
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        // Return the decoded "SECRET_KEY"
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 4 Retrieval of "username/email" (JWT subject)
    @Override // Extract a single Claim
    public <T> T getClaim(String jwtToken, Function<Claims, T> claimsResolver) { // E
        final Claims claims = getAllClaims(jwtToken);

        // claims ==> the one for the list of all the Claims
        return claimsResolver.apply(claims);
    }
}
