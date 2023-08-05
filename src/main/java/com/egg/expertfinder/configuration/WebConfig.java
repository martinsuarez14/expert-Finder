package com.egg.expertfinder.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configurar el manejador para servir archivos estáticos (como JavaScript) desde una ubicación específica
        registry.addResourceHandler("/static/js") // Aquí define la ruta donde estarán tus archivos JavaScript
                .addResourceLocations("classpath:/static/js"); // Ruta física dentro del proyecto donde se encuentran los archivos JavaScript
    }
}
