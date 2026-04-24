package com.asier.SistemaReservas.system.auth.service.impl;

import com.asier.SistemaReservas.airline.employee.domain.enums.EmployeeType;
import com.asier.SistemaReservas.airline.employee.service.AirlineEmployeeInfoService;
import com.asier.SistemaReservas.aiport.service.AirportService;
import com.asier.SistemaReservas.airline.service.AirlineHelper;
import com.asier.SistemaReservas.hotel.service.HotelHelper;
import com.asier.SistemaReservas.loyalty.service.LoyaltyService;
import com.asier.SistemaReservas.system.auth.records.LoginRequest;
import com.asier.SistemaReservas.system.auth.records.RegisterRequest;
import com.asier.SistemaReservas.system.auth.records.TokenResponse;
import com.asier.SistemaReservas.airline.employee.domain.entity.AirlineEmployeeInfoEntity;
import com.asier.SistemaReservas.hotel.employee.domain.entity.HotelEmployeeInfoEntity;
import com.asier.SistemaReservas.hotel.employee.service.HotelEmployeeInfoService;
import com.asier.SistemaReservas.hotel.service.HotelService;
import com.asier.SistemaReservas.system.JWT.service.JwtService;
import com.asier.SistemaReservas.system.JWT.domain.entity.Token;
import com.asier.SistemaReservas.system.JWT.repository.TokenRepository;
import com.asier.SistemaReservas.system.JWT.domain.enums.TokenType;
import com.asier.SistemaReservas.system.auth.service.AuthService;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.user.event.records.UserCreatedEvent;
import com.asier.SistemaReservas.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
    private final AirlineEmployeeInfoService airportEmployeeInfoService;
    private final HotelEmployeeInfoService hotelEmployeeInfoService;
    private final AirportService airportService;
    private final HotelHelper hotelHelper;
    private final AirlineHelper airlineHelper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public TokenResponse register(RegisterRequest register) {
        UserEntity user = UserEntity.builder()
                .mail(register.getMail())
                .name(register.getName())
                .password(passwordEncoder.encode(register.getPassword()))
                .userRole(register.getRole())
                .createdAt(LocalDateTime.now())
                .build();

        UserEntity savedUser = userService.createUser(user);

        createWorkerByRole(savedUser, register);

        eventPublisher.publishEvent(new UserCreatedEvent(savedUser.getId(), savedUser.getName(), savedUser.getMail()));

        String jwtToken = jwtService.generateToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);
        saveUserToken(savedUser, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    private void createWorkerByRole(UserEntity user, RegisterRequest request){
        switch(user.getUserRole()){
            case HOTEL_WORKER:
                if(request.getHotelId() != null){
                    HotelEmployeeInfoEntity hotelEmployeeInfo = HotelEmployeeInfoEntity.builder()
                            .user(user)
                            .hotel(hotelHelper.getHotelEntity(request.getHotelId()))
                            .employeeType(request.getEmployeeType())
                            .build();
                    hotelEmployeeInfoService.createHotelEmployee(hotelEmployeeInfo);
                }
                break;
            case AIRLINE_WORKER:
                if(request.getAirportId() != null || request.getEmployeeType() == EmployeeType.ADMIN){
                    AirlineEmployeeInfoEntity airportEmployeeInfo = AirlineEmployeeInfoEntity.builder()
                            .user(user)
                            .airport(airportService.getAirport(request.getAirportId()))
                            .airline(airlineHelper.getAirline(request.getAirlineId()))
                            .type(request.getEmployeeType())
                            .build();
                    airportEmployeeInfoService.createAirlineEmployee(airportEmployeeInfo);
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
