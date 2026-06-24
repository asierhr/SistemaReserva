package com.asier.SistemaReservas.users.integrationTests;

import com.asier.SistemaReservas.config.BaseIntegrationTest;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.user.repository.UserRepository;
import com.asier.SistemaReservas.user.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Testcontainers
@Transactional
public class UserIntegrationTests extends BaseIntegrationTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "asier@gmail.com")
    void getUserEntityWhenAuthenticated() {
        UserEntity user = new UserEntity();
        user.setMail("asier@gmail.com");
        user.setName("asier");
        userRepository.save(user);

        UserEntity result = userService.getUserEntity();

        assertThat(result).isNotNull();
        assertThat(result.getMail()).isEqualTo("asier@gmail.com");
    }
}
