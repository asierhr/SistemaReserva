package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.entities.UserEntity;
import com.asier.SistemaReservas.repositories.UserRepository;
import com.asier.SistemaReservas.servicies.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<UserEntity> getUserByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    @Override
    public UserEntity getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            System.out.println("authentication null");
            return null;
        }
        String mail = authentication.getName();
        return userRepository.findByMail(mail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
