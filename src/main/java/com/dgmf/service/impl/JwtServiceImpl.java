package com.dgmf.service.impl;

import com.dgmf.service.JwtService;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {
    // 4.
    @Override
    public String getUsernameFromToken(String jwtToken) {
        return null;
    }
}
