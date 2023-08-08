package com.dgmf.service;

import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.function.Function;

public interface JwtKeyProcessingService {
    Claims getAllClaims(String jwtToken);
    Key getSigningKey();
    <T> T getClaim(String jwtToken, Function<Claims, T> claimsResolver);
}
