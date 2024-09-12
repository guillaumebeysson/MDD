package com.openclassrooms.back.configuration;

import com.openclassrooms.back.services.CustomUserDetailsServiceImpl;
import com.openclassrooms.back.services.JwtServiceImpl;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;

    /***
     * Filtre qui vérifie la présence d'un token JWT dans le header de la requête.
     * Si le token est valide, l'utilisateur est authentifié.
     * @param request La requête
     * @param response La réponse
     * @param filterChain La chaîne de filtres
     */
    protected void doFilterInternal2(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String userEmail;
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUserName(jwt);

        try {
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (JwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token: " + e.getMessage());
            return;
        }

//        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//            if (!jwtService.isTokenValid(jwt, userDetails)) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
//                return;
//            }
//            if (jwtService.isTokenValid(jwt, userDetails)) {
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
        filterChain.doFilter(request, response);
    }

    /***
     * Filtre qui vérifie la présence d'un token JWT dans les cookies de la requête.
     * Si le token est valide, l'utilisateur est authentifié.
     * @param request La requête
     * @param response La réponse
     * @param filterChain La chaîne de filtres
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String jwt = null;
        String userEmail = null;

        // Récupération du token depuis les cookies
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        // Si le JWT a été trouvé dans le cookie, extraire l'email
        if (jwt != null) {
            try {
                userEmail = jwtService.extractUserName(jwt);
            } catch (JwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token: " + e.getMessage());
                return;
            }
        }

        // Validation du JWT et configuration de l'authentification dans le contexte de sécurité
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continuer avec le reste de la chaîne de filtres
        filterChain.doFilter(request, response);
    }
}
