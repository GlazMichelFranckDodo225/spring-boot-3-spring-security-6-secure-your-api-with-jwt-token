package com.dgmf.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
1.	JwtAuthenticationFilter
When a Client sends a Request, the first thing that will be executed is
the « JwtAuthenticationFilter ».
The « JwtAuthenticationFilter » has the role to validate and check everything
regarding the JWT Token that the Client owned or not.
"OncePerRequestFilter" ==> The « JwtAuthenticationFilter » will be activated
every time the Application gets a Request
*/
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            // Chain of Responsibility Design Pattern
            // Contains the list of the Filters
            @NonNull FilterChain filterChain
        ) throws ServletException, IOException {
    }
}
