//package hu.jakab.ekkeencoprosampbackend.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//
//        config.setAllowCredentials(true);
//        config.setAllowedOrigins(List.of("http://localhost:4200")); //
//        config.setAllowedHeaders(Arrays.asList(
//                "Origin", "Content-Type", "Accept", "Authorization",
//                "Access-Control-Allow-Headers", "Access-Control-Request-Method",
//                "Access-Control-Request-Headers"
//        ));
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
//
//
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//}
