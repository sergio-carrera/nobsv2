package com.example.nobsv2.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        //This can be coded more elegantly, but this is just to keep it simple

        String authHeader = request.getHeader("Authorization");
        String token = null;

        //Auhorization comes in the header with a key of Authorization but
        //is precede it by Bearer  [jwt]
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
        }

        if (token != null && JwtUtil.isTokenValid(token)) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    JwtUtil.getClaims(token).getSubject(),
                    null, //credentials are null because we verified the credentials calling isTokenValid
                    Collections.emptyList() //roles & authorities that we currently don't have
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);

    }
}
