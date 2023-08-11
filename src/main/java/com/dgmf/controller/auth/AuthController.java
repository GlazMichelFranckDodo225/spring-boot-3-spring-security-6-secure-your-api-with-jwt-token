package com.dgmf.controller.auth;

import com.dgmf.dto.LoginRequestUserDTO;
import com.dgmf.dto.RegisterRequestUserDTO;
import com.dgmf.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // Takes the Registration Request and Responds with a Token
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequestUserDTO registerRequest) {
        System.out.println("Stack Trace - AuthController - register()");

        return ResponseEntity.ok(authService.register(registerRequest));
    }

    // Takes the Authentication Request and Responds with a Token
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequestUserDTO loginRequest)
        {
        System.out.println("Stack Trace - AuthController - login()");

        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
