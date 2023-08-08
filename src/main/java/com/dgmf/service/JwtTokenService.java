package com.dgmf.service;

public interface JwtTokenService {
    // 4.
    String getUsernameFromToken(String jwtToken);
}
