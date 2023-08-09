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

    // Bean of Type "AuthenticationProvider"
    // DAO Object which is responsible to fetch the "User Details"
    // from the DB, to encode Password and so forth
    @Bean
    public AuthenticationProvider authenticationProvider() { // C
        DaoAuthenticationProvider authenticationProvider =
                new DaoAuthenticationProvider();
        // Which User Details Service to use in order to fetch User
        // Information (there are multiple implementations of "UserDetails")
        // ==> Takes the one just above
        authenticationProvider.setUserDetailsService(userDetailsService());
        // Which Password Encoder  to use ==> Takes the one just below
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    // Bean of Type "PasswordEncoder"
    @Bean
    public PasswordEncoder passwordEncoder() { // D
        return new BCryptPasswordEncoder();
    }

    // Bean of Type "AuthenticationManager"
    // The one responsible to manage Authentication
    // The "AuthenticationConfiguration" holds the Information
    // about the "AuthenticationManager"
    @Bean
    public AuthenticationManager authenticationManager( // E
            AuthenticationConfiguration configuration) throws Exception
    {
        return configuration.getAuthenticationManager();
    }
}
