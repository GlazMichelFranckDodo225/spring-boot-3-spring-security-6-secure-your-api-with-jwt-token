package com.dgmf.config;

import com.dgmf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Hold all Application configurations such as "Beans" and so forth
// At the startup, Spring pickup this Class, implement and inject all
// declared inside
@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final UserRepository userRepository; // B

    // Bean of Type "UserDetailsService"
    @Bean
    public UserDetailsService userDetailsService() { // A
        return username -> userRepository
                .findByEmail(username) // "username" ==> "userEmail"
                .orElseThrow(
                        () -> new UsernameNotFoundException("User Not Found")
                );
    }
}
