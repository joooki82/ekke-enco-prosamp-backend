//package hu.jakab.ekkeencoprosampbackend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {//
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("http://localhost:4200")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
//                .allowCredentials(true);
//    }
//}

//    private final AuditInterceptor auditInterceptor;
//
//    public WebConfig(AuditInterceptor auditInterceptor) {
//        this.auditInterceptor = auditInterceptor;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(auditInterceptor);
//    }
//}
