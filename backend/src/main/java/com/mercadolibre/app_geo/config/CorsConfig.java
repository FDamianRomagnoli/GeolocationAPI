package com.mercadolibre.app_geo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer { // Aquí implementas WebMvcConfigurer

    @Override
    public void addCorsMappings(CorsRegistry registry) { // Método para configurar CORS
        registry.addMapping("/**") // Aplica CORS a todas las rutas
                .allowedOrigins("http://localhost:3000") // Permite solicitudes de localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
                .allowedHeaders("*"); // Permite todos los headers
    }
}