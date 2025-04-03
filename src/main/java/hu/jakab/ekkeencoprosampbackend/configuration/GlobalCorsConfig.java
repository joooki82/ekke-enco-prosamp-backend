//package hu.jakab.ekkeencoprosampbackend.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class GlobalCorsConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")  // Apply to all endpoints
//                .allowedOrigins("http://localhost:4200")  // Your Angular frontend
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow common HTTP methods
//                .allowedHeaders("Authorization", "Content-Type", "Accept", "Origin")  // Allow necessary headers
//                .allowCredentials(true);  // Allow cookies and credentials
//    }
//}
