package com.catalogue.musique.filter;

import com.catalogue.musique.security.TokenBlacklist;
import com.catalogue.musique.services.TokenBlacklistService;
import com.catalogue.musique.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final TokenBlacklistService tokenBlacklist;

    @Autowired
    public JwtRequestFilter(JwtUtil jwtUtil, @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, TokenBlacklistService tokenBlacklist) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.tokenBlacklist = tokenBlacklist;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
                log.info("Filtre JWT appliqué");

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractSubject(jwt);
            if (tokenBlacklist.isTokenBlacklisted(jwt)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, username)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                        log.info("Authentification réussie pour l'utilisateur : {}", authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Authentification réussie pour l'utilisateur : {}", username);
                log.info("Rôles de l'utilisateur : {}", userDetails.getAuthorities());
            } else {
                log.warn("Token JWT invalide pour l'utilisateur : {}", username);
            }
        }


        chain.doFilter(request, response);
    }
}