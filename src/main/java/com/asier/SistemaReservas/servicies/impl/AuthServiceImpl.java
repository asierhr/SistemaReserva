package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.auth.LoginRequest;
import com.asier.SistemaReservas.auth.RegisterRequest;
import com.asier.SistemaReservas.auth.TokenResponse;
import com.asier.SistemaReservas.domain.entities.AirportEmployeeInfoEntity;
import com.asier.SistemaReservas.domain.entities.HotelEmployeeInfoEntity;
import com.asier.SistemaReservas.domain.entities.Token;
import com.asier.SistemaReservas.domain.entities.UserEntity;
import com.asier.SistemaReservas.domain.enums.TokenType;
import com.asier.SistemaReservas.repositories.TokenRepository;
import com.asier.SistemaReservas.servicies.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AirportEmployeeInfoService airportEmployeeInfoService;
    private final HotelEmployeeInfoService hotelEmployeeInfoService;
    private final AirportService airportService;
    private final HotelService hotelService;

    @Override
    public TokenResponse register(RegisterRequest register) {
        UserEntity user = UserEntity.builder()
                .mail(register.mail())
                .name(register.name())
                .password(passwordEncoder.encode(register.password()))
                .userRole(register.role())
                .createdAt(LocalDateTime.now())
                .build();

        UserEntity savedUser = userService.createUser(user);

        createWorkerByRole(savedUser, register);

        String jwtToken = jwtService.generateToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);
        saveUserToken(user, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    private void createWorkerByRole(UserEntity user, RegisterRequest request){
        switch(user.getUserRole()){
            case HOTEL_WORKER:
                if(request.hotelId() != null){
                    HotelEmployeeInfoEntity hotelEmployeeInfo = HotelEmployeeInfoEntity.builder()
                            .user(user)
                            .hotel(hotelService.getHotelEntity(request.hotelId()))
                            .build();
                    hotelEmployeeInfoService.createHotelEmployee(hotelEmployeeInfo);
                }
                break;
            case AIRPORT_WORKER:
                if(request.airportId() != null){
                    AirportEmployeeInfoEntity airportEmployeeInfo = AirportEmployeeInfoEntity.builder()
                            .user(user)
                            .airport(airportService.getAirport(request.airportId()))
                            .build();
                    airportEmployeeInfoService.createAirportEmployee(airportEmployeeInfo);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public TokenResponse login(LoginRequest login) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.mail(),
                        login.password()
                )
        );
        UserEntity user = userService.getUserByMail(login.mail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    @Override
    public void saveUserToken(UserEntity user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public void revokeAllUserTokens(UserEntity user) {
        final List<Token> validUserTokens = tokenRepository.findAllInvalidOrRevokedTokensByUserId(user.getId());
        if(!validUserTokens.isEmpty()){
            for(final Token token: validUserTokens){
                token.setExpired(true);
                token.setRevoked(true);
            }
            tokenRepository.saveAll(validUserTokens);
        }
    }
}
