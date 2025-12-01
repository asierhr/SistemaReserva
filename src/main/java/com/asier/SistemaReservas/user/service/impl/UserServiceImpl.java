package com.asier.SistemaReservas.user.service.impl;

import com.asier.SistemaReservas.user.domain.DTO.UserDTO;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.user.mapper.UserMapper;
import com.asier.SistemaReservas.user.repository.UserRepository;
import com.asier.SistemaReservas.user.service.UserService;
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
    private final UserMapper userMapper;

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<UserEntity> getUserByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    @Override
    public UserDTO getUser() {
        UserEntity user = getUserEntity();
        return userMapper.toDTO(user);
    }

    @Override
    public UserEntity getUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            System.out.println("authentication null");
            return null;
        }
        String mail = authentication.getName();
        return userRepository.findByMail(mail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
