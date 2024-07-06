package com.jeevanraksha.billing_service.jwt;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter  extends OncePerRequestFilter{

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header from billing: " + authorizationHeader);
        String username = null;
        String jwt = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }
        System.out.println("Username from billing: " + username);
        System.out.println("JWT from billing: " + jwt);
        if (username != null) {
            // UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UserDetails userDetails = new User(username, "", new ArrayList<>());
            System.out.println("User Details from billing: " + userDetails);
            System.out.println("JWT Valid from billing: " + jwtUtil.validateToken(jwt));
            if (jwtUtil.validateToken(jwt)) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities().stream().toList());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.println("Setting Authentication from billing: " + auth);
                SecurityContextHolder.getContext().setAuthentication(auth);
                request.setAttribute("user", userDetails);
            }
        }
        chain.doFilter(request, response);
    }
}