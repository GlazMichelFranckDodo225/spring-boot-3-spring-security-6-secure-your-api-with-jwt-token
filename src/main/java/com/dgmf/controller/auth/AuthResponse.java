package com.dgmf.controller.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthResponse {
    // The JWT Token that will be sent back to the Client
    private String jwtToken;
}
