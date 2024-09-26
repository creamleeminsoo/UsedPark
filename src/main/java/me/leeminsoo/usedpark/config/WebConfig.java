package me.leeminsoo.usedpark.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer { // CORS설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/subscribe/**")
                .allowedOrigins("http://localhost:8080") // 허용할 Origin
                .allowedMethods("GET")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
