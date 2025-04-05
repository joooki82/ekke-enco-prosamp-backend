//package hu.jakab.ekkeencoprosampbackend.configuration;
//
//import hu.jakab.ekkeencoprosampbackend.filters.UserIdSettingFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Bean
//    public FilterRegistrationBean<UserIdSettingFilter> userIdSettingFilter(DataSource dataSource) {
//        FilterRegistrationBean<UserIdSettingFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new UserIdSettingFilter(dataSource));
//        registrationBean.addUrlPatterns("/api/*"); // Apply to all API requests
//        return registrationBean;
//    }
//}
