package com.asier.SistemaReservas;

import com.asier.SistemaReservas.config.BaseIntegrationTest;
import com.asier.SistemaReservas.config.DotenvInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles("local-test")
@ContextConfiguration(initializers = DotenvInitializer.class)
class SistemaReservasApplicationTests extends BaseIntegrationTest {

	@Test
	void contextLoads() {
	}

}
