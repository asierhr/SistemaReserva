package com.asier.SistemaReservas.system.config;

import com.asier.SistemaReservas.system.JWT.domain.entity.Token;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.system.JWT.repository.TokenRepository;
import com.asier.SistemaReservas.system.JWT.service.JwtService;
import com.asier.SistemaReservas.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        if(request.getServletPath().contains("/auth") || request.getServletPath().startsWith("/flights/")){
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwtToken = authHeader.substring(7);
            final String mail = jwtService.extractUser(jwtToken);

            if(mail != null && SecurityContextHolder.getContext().getAuthentication() == null){
                final Token token = tokenRepository.findByToken(jwtToken);

                if(token != null && !token.isExpired() && !token.isRevoked()){
                    final UserDetails userDetails = this.userDetailsService.loadUserByUsername(mail);
                    final Optional<UserEntity> user = userService.getUserByMail(userDetails.getUsername());

                    if(user.isPresent() && jwtService.isTokenValid(jwtToken, user.get())){
                        final var authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
        } catch (Exception e) {
        }

        filterChain.doFilter(request, response);
    }
}
