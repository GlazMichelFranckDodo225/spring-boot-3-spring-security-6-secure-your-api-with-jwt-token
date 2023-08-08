package com.dgmf.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// This Class tells Spring which Configuration to use for
// binding all the JWT Token Process
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    /* At the Application startup, Spring Security will try to look
    for a "Bean" of Type "SecurityFilterChain". This "SecurityFilterChain"
    is responsible for the configuration of the entire HTTP Security of
    the Application */
    // "Bean" of Type "SecurityFilterChain"
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception
    {
        http
                .csrf(csrf -> csrf.disable())
                // Processing of Requests
                .authorizeHttpRequests(
                    authRequest -> authRequest
                        // Endpoints white list which do not require any
                        // authentication or JWT Token
                        .requestMatchers("").permitAll()
                        // All the other Requests require authentication
                        .anyRequest().authenticated()
                )
                /* "Session Management" Policy ==> Don't store "Authentication
                State" or "Session state" ==> Every Request must be
                authenticated (OncePerRequestFilter) ==> Spring will
                create a new Session for each Request */
                .sessionManagement(
                        httpSecuritySessionManagementConfigurer ->
                                httpSecuritySessionManagementConfigurer
                                        .sessionCreationPolicy(
                                                SessionCreationPolicy.STATELESS)
                )
                // Tells to Spring which "Authentication Provider" to use
                .authenticationProvider(authenticationProvider)
                // Allows adding a Filter before one of the known Filter classes
                // Use "jwtAuthenticationFilter" before "UsernamePasswordAuthenticationFilter"
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
