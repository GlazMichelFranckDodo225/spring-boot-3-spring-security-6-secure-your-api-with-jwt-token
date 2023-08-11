package com.dgmf.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.dgmf.service.JwtKeyProcessingService;
import com.dgmf.service.JwtTokenProcessingService;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtTokenProcessingService, JwtKeyProcessingService {
    private static final String SECRET_KEY = "d525f722439b5a9df23de67911200fd3a4ff692dc00363630034826edb925d84"; // C

    // 4. Retrieval of "email" (JWT subject)
    // Before implementing this method, we need to implement
    // the process to extract Claims
    @Override
    public String extractUserEmailBasedOnJwtToken(String jwtToken) { // A
        System.out.println("Stack Trace - JwtServiceImpl - getUsernameFromToken()");

        // Subject ==> "username" or "email" in the JWT Token world
        return extractSingleClaim(jwtToken, Claims::getSubject); // E
    }

    // To generate a JWT Token out of Extracted Claims and UserDetails
    // Map<String, Object> contains the extracted Claims that
    // we want to add
    @Override
    public String generateToken(
            Map<String, Object> extraClaims, UserDetails userDetails) { // F
        System.out.println("Stack Trace - JwtServiceImpl - generateToken() - 2");

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername()) // Username ==> User Email
                // When the Claim was created, this will help to make calculations
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // The JWT Token will be valid for 24 hours
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                // Generates and returns the JWT Token
                .compact();
    }

    // To generate a JWT Token without having Extracted
    // Claims, just only using UserDetails itself
    @Override
    public String generateToken(UserDetails userDetails) { // G
        System.out.println("Stack Trace - JwtServiceImpl - generateToken() - 1");

        // Assigning a Token to the saved User
        return generateToken(new HashMap<>(), userDetails);
    }

    // To validate if the JWT Token belongs to the saved User
    @Override
    public boolean isTokenValid(String jwtToken, UserDetails userDetails) { // J
        // "username" ==> "userEmail"
        final String email = extractUserEmailBasedOnJwtToken(jwtToken);

        System.out.println("Stack Trace - JwtServiceImpl - isTokenValid()");

        // Returns "true" if the "User Email" of the saved User is the same
        // that the one inputted and if the JWT Token is not expired
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    @Override
    public boolean isTokenExpired(String jwtToken) { // I
        System.out.println("Stack Trace - JwtServiceImpl - isTokenExpired()");

        // Returns "true" if the expiration date has not passed with
        // respect to the current date
        return extractExpiration(jwtToken).before(new Date());
    }

    @Override
    public Date extractExpiration(String jwtToken) { // H
        System.out.println("Stack Trace - JwtServiceImpl - getExpiration()");

        // Retrieves Expiration date from the Payload Claims
        return extractSingleClaim(jwtToken, Claims::getExpiration);
    }

    // 4 Retrieval of "username/email" (JWT subject)
    // Extract all Claims
    @Override
    public Claims extractAllClaims(String jwtToken) { // B
        System.out.println("Stack Trace - JwtServiceImpl - getAllClaims() - 1");

        return Jwts
                .parserBuilder()
                // The "SigningKey" is the "Secret Key" needed to try
                // to generate or decode a JWT Token
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                // Retrieval all the Claims from the Payload
                .getBody();
    }

    @Override
    public Key getSigningKey() { // D
        // Decode the "SECRET_KEY"
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        System.out.println("Stack Trace - JwtServiceImpl - getSigningKey()");

        // Return the decoded "SECRET_KEY"
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 4 Retrieval of "username/email" (JWT subject)
    // Extract a single Claim
    @Override
    public <T> T extractSingleClaim(
            String jwtToken,
            Function<Claims, T> claimsResolver
        ) { // E
        final Claims claims = extractAllClaims(jwtToken);

        System.out.println("Stack Trace - JwtServiceImpl - getClaim() - 2");

        // We get the one for the list of all Claims, and return it
        return claimsResolver.apply(claims);
    }
}
