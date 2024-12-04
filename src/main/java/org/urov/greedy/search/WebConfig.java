package org.urov.greedy.search;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Разрешаем доступ к любым эндпоинтам, начинающимся с /api/
                .allowedOrigins("http://localhost:63342")  // Разрешаем запросы с этого URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Разрешаем только эти методы
                .allowedHeaders("*")  // Разрешаем все заголовки
                .allowCredentials(true);  // Разрешаем отправку куки и аутентификационных данных
    }
}

