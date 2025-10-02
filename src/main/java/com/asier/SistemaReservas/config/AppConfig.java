package com.asier.SistemaReservas.config;

import com.asier.SistemaReservas.domain.entities.UserEntity;
import com.asier.SistemaReservas.servicies.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return mail -> {
            final UserEntity user = userService.getUserByMail(mail)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getUserRole().name());

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getMail())
                    .password(user.getPassword())
                    .authorities(authority)
                    .build();
        };
    }

    @SuppressWarnings("deprecation")
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
