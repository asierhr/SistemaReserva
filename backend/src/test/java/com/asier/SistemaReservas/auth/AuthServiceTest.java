package com.asier.SistemaReservas.auth;

import com.asier.SistemaReservas.auth.objects.AuthObjects;
import com.asier.SistemaReservas.system.JWT.domain.entity.Token;
import com.asier.SistemaReservas.system.JWT.domain.enums.TokenType;
import com.asier.SistemaReservas.system.JWT.repository.TokenRepository;
import com.asier.SistemaReservas.system.JWT.service.JwtService;
import com.asier.SistemaReservas.system.auth.records.LoginRequest;
import com.asier.SistemaReservas.system.auth.records.RegisterRequest;
import com.asier.SistemaReservas.system.auth.records.TokenResponse;
import com.asier.SistemaReservas.system.auth.service.impl.AuthServiceImpl;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.user.domain.enums.UserRole;
import com.asier.SistemaReservas.user.event.records.UserCreatedEvent;
import com.asier.SistemaReservas.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private AuthServiceImpl authService;


    @Test
    void testRegisterUser(){
        RegisterRequest request = AuthObjects.valid();
        UserEntity savedUser = UserEntity.builder()
                .id(1L)
                .mail(request.getMail())
                .password("encodedPassword")
                .name(request.getName())
                .userRole(request.getRole())
                .build();
        when(passwordEncoder.encode(anyString()))
                .thenReturn("encodedPassword");
        when(userService.createUser(any(UserEntity.class)))
                .thenReturn(savedUser);
        when(jwtService.generateToken(savedUser))
                .thenReturn("jwt-token");
        when(jwtService.generateRefreshToken(savedUser))
                .thenReturn("refresh-token");

        TokenResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("jwt-token",response.accessToken());
        assertEquals("refresh-token",response.refreshToken());

        verify(userService).createUser(any(UserEntity.class));
        verify(eventPublisher).publishEvent(any(UserCreatedEvent.class));
    }

    @Test
    void testRegisterUserMailDuplicate(){
        RegisterRequest request = AuthObjects.valid();

        when(userService.createUser(any(UserEntity.class)))
                .thenThrow(new IllegalArgumentException("Email already exists"));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.register(request)
        );

        assertEquals("Email already exists", exception.getMessage());

        verify(userService).createUser(any(UserEntity.class));

        verifyNoInteractions(jwtService);
        verifyNoInteractions(eventPublisher);
    }

    @Test
    void testLoginUser(){
        LoginRequest login = AuthObjects.login();
        UserEntity user = UserEntity.builder()
                .id(1L)
                .mail("testObject@gmail.com")
                .name("testPassword")
                .userRole(UserRole.USER)
                .build();

        when(userService.getUserByMail("testObject@gmail.com"))
                .thenReturn(Optional.of(user));

        when(jwtService.generateToken(user))
                .thenReturn("jwt-token");

        when(jwtService.generateRefreshToken(user))
                .thenReturn("refresh-token");

        TokenResponse response = authService.login(login);

        assertNotNull(response);
        assertEquals("jwt-token", response.accessToken());
        assertEquals("refresh-token", response.refreshToken());

        verify(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenRepository).save(any(Token.class));
        verify(userService).getUserByMail("testObject@gmail.com");
    }

    @Test
    void testLoginUserNotFound(){
        LoginRequest loginRequest = AuthObjects.login1();
        when(userService.getUserByMail("testObject1@gmail.com")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> authService.login(loginRequest));
    }

    @Test
    void testLoginIncorrectPassword(){
        LoginRequest loginRequest = AuthObjects.login(); // email y password de prueba

        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        BadCredentialsException exception = assertThrows(
                BadCredentialsException.class,
                () -> authService.login(loginRequest)
        );

        assertEquals("Bad credentials", exception.getMessage());

        verify(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        verifyNoInteractions(userService);
        verifyNoInteractions(jwtService);
    }

    @Test
    void testSaveUserToken(){
        RegisterRequest request = AuthObjects.valid();
        UserEntity savedUser = UserEntity.builder()
                .id(1L)
                .mail(request.getMail())
                .password("encodedPassword")
                .name(request.getName())
                .userRole(request.getRole())
                .build();
        String jwtToken = "jwt-token";

        authService.saveUserToken(savedUser,jwtToken);

        ArgumentCaptor<Token> captor = ArgumentCaptor.forClass(Token.class);
        verify(tokenRepository).save(captor.capture());

        Token savedToken = captor.getValue();

        assertEquals(savedUser,savedToken.getUser());
        assertEquals(jwtToken,savedToken.getToken());
        assertEquals(TokenType.BEARER,savedToken.getTokenType());
        assertFalse(savedToken.isExpired());
        assertFalse(savedToken.isRevoked());
    }
}
