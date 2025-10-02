package com.asier.SistemaReservas.config;

import com.asier.SistemaReservas.domain.entities.Token;
import com.asier.SistemaReservas.repositories.TokenRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;
    private final TokenRepository tokenRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/auth/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/auth/logout")
                                .addLogoutHandler((request, response, authentication) -> {
                                    final var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                                    logout(authHeader);
                                })
                                .logoutSuccessHandler((request, response, authentication) -> {
                                    SecurityContextHolder.clearContext();
                                    response.setStatus(HttpServletResponse.SC_OK);
                                    response.getWriter().write("{\"message\": \"Logged out successfully\"}");
                                })
                );
        return http.build();
    }

    private void logout(final String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return;
        }

        final String jwtToken = token.substring(7);
        final Token foundToken = tokenRepository.findByToken(jwtToken);
        if (foundToken != null) {
            foundToken.setExpired(true);
            foundToken.setRevoked(true);
            tokenRepository.save(foundToken);
        }
    }

}
