package com.dgmf.service;

import com.dgmf.controller.auth.AuthResponse;
import com.dgmf.dto.LoginRequestUserDTO;
import com.dgmf.dto.RegisterRequestUserDTO;

public interface AuthService {
    AuthResponse register(RegisterRequestUserDTO registerRequest);
    AuthResponse login(LoginRequestUserDTO loginRequest);
}
