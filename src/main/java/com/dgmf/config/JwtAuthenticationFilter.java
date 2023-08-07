package com.dgmf.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
        // 2. The « JwtAuthenticationFilter » checks if the Client
        // has a JWT Token or not, and extracts it if it is present.
        final String jwtToken = getTokenFromRequest(request);

        // 3. If the JWT Token is missing (null)
        if(jwtToken == null) {
            // Passing the "Request" and the "Response" to the
            // next Filter
            filterChain.doFilter(request, response);

            return;
        }
    }

    // 2.
    private String getTokenFromRequest(HttpServletRequest request) {
        // Retrieval of the "Authorization" Header
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Checking if the "Authorization" Header contains the
        // "Bearer Token" (JWT Token)
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            // If yes, returns "Bearer Token"
            return authHeader.substring(7);
        }

        // Return "null" if the "Bearer Token" is missing in
        // the "Authorization" Header
        return null;
    }
}
