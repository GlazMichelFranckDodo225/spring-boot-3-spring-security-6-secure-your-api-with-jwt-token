package com.dgmf.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtTokenProcessingService {
    // 4.
    String getUsernameFromToken(String jwtToken);
    String generateToken(Map<String, Object> extraClaims, UserDetails savedUser);
    String generateToken(UserDetails savedUser);
}
