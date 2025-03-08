//package hu.jakab.ekkeencoprosampbackend.configuration;
//
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
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
