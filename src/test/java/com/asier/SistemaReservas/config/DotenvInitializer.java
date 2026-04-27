package com.asier.SistemaReservas.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import java.util.HashMap;
import java.util.Map;

public class DotenvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Dotenv dotenv = Dotenv.configure()
                .directory("./") // Busca en la raíz del proyecto
                .ignoreIfMissing()
                .load();

        Map<String, Object> envProps = new HashMap<>();
        dotenv.entries().forEach(entry -> envProps.put(entry.getKey(), entry.getValue()));

        // Insertamos las variables al principio de la lista de propiedades de Spring
        applicationContext.getEnvironment().getPropertySources().addFirst(
                new MapPropertySource("dotenvProperties", envProps)
        );
    }
}
