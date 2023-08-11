package com.dgmf.service.impl;

import com.dgmf.controller.auth.AuthResponse;
import com.dgmf.dto.LoginRequestUserDTO;
import com.dgmf.dto.RegisterRequestUserDTO;
import com.dgmf.entity.User;
import com.dgmf.entity.enums.Role;
import com.dgmf.repository.UserRepository;
import com.dgmf.service.AuthService;
import com.dgmf.service.JwtTokenProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProcessingService jwtTokenProcessingService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // This Method allows to create a "User", save him into the DB and
    // returns, to the Client, the generated JWT Token related to him
    @Override
    public AuthResponse register(
            RegisterRequestUserDTO registerRequestUserDTO)
    {
        // Creates the "User" from the coming Request
        var user = User.builder()
                .firstName(registerRequestUserDTO.getFirstName())
                .lastName(registerRequestUserDTO.getLastName())
                //.userUsername(registerRequestUserDTO.getUserUsername())
                .email(registerRequestUserDTO.getEmail())
                .password(passwordEncoder.encode(
                                registerRequestUserDTO.getPassword()
                        )
                )
                .role(Role.ROLE_USER) // Default Role
                .build();

        // User savedUser = userRepository.save(user);
        // Saves the "User" that we've just created
        userRepository.save(user);

        // Generates the JWT Token for the just saved User
        var jwtTokenSavedUser = jwtTokenProcessingService.generateToken(user);

        System.out.println("Stack Trace - AuthServiceImpl - register()");

        // Returns the generated JWT Token to the Client
        return AuthResponse.builder()
                //.token(jwtService.getToken(savedUser))
                .jwtToken(jwtTokenSavedUser)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequestUserDTO loginRequestUserDTO) {
        // The "AuthenticationManager" will check "Email" and "Password"
        // An Exception will throw if the "Email" or " Password"
        // are not correct
        authenticationManager.authenticate( /*
                .authenticate() ==> Method of the "Authentication Manager" which
                allows to authenticate "User" based on his "Email" and "Password"
                */
                new UsernamePasswordAuthenticationToken(
                        loginRequestUserDTO.getEmail(),
                        loginRequestUserDTO.getPassword()
                )
        );

        // Here, "Email" and "Password" are correct.
        // Next step ==> Find the User into the DB based on
        // his Email coming from the Request
        var userFound = userRepository
                .findByEmail(loginRequestUserDTO.getEmail()).orElseThrow();

        // Generates a JWT Token for the retrieved User
        var jwtTokenUserFound = jwtTokenProcessingService.generateToken(userFound);

        System.out.println("Stack Trace - AuthServiceImpl - login()");

        // Returns the generated JWT Token to the Client
        return AuthResponse.builder()
                .jwtToken(jwtTokenUserFound)
                .build();
    }
}
