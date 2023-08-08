package com.dgmf.service;

public interface JwtTokenProcessingService {
    // 4.
    String getUsernameFromToken(String jwtToken);
}
