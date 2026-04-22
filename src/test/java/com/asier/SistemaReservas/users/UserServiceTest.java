package com.asier.SistemaReservas.users;

import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.user.mapper.UserMapper;
import com.asier.SistemaReservas.user.repository.UserRepository;
import com.asier.SistemaReservas.user.service.impl.UserServiceImpl;
import com.asier.SistemaReservas.users.objects.UserObjects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testCreatedUser(){
        UserEntity user = UserObjects.user();
        when(userRepository.save(user)).thenReturn(UserEntity.builder()
                        .id(1L)
                        .mail(user.getMail())
                        .name(user.getName())
                        .password(user.getPassword())
                        .userRole(user.getUserRole())
                        .createdAt(user.getCreatedAt())
                        .build()
        );
        UserEntity saved = userService.createUser(user);
        assertNotNull(saved.getId());
        verify(userRepository).save(user);
    }

    @Test
    void testGetUserByMail(){
        UserEntity user = UserObjects.user();
        when(userRepository.findByMail(user.getMail())).thenReturn(Optional.of(user));
        Optional<UserEntity> result = userService.getUserByMail(user.getMail());

        assertTrue(result.isPresent());
        assertEquals(user.getMail(),result.get().getMail());
        verify(userRepository).findByMail(user.getMail());
    }

    @Test
    void testGetUserByMailNotFound(){
        when(userRepository.findByMail("missing@gmail.com")).thenReturn(Optional.empty());
        Optional<UserEntity> result = userService.getUserByMail("missing@gmail.com");
        assertFalse(result.isPresent());
        verify(userRepository).findByMail("missing@gmail.com");
    }
}
