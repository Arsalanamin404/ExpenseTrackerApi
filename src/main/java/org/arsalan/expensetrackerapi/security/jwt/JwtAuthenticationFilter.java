package org.arsalan.expensetrackerapi.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.arsalan.expensetrackerapi.security.service.CustomUserDetailsService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = parseBearerToken(request);

        if (token != null) {
            System.out.println("TOKEN = " + token);
            try {
                String username = jwtService.extractUsername(token);

                if (username != null && SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

                    System.out.println("USERNAME = " + username);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    System.out.println("USER FOUND = " + userDetails.getUsername());

                    if (jwtService.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                        SecurityContextHolder
                                .getContext()
                                .setAuthentication(authentication);
                    }
                }
            } catch (ExpiredJwtException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("""
                        {
                        "success": false,
                        "statusCode": 401,
                        "error": "Invalid or Expired Token"
                        }
                        """);
                return;
            } catch (JwtException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");

                response.getWriter().write("""
                        {
                            "success": false,
                            "statusCode": 401,
                            "error": "Invalid or Expired Token"
                        }
                        """);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
