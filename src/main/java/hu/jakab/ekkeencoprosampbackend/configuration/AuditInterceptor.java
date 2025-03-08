//package hu.jakab.ekkeencoprosampbackend.configuration;
//
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.stereotype.Component;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component
//public class AuditInterceptor implements HandlerInterceptor {
//
//    private final AuditUserService auditUserService;
//
//    public AuditInterceptor(AuditUserService auditUserService) {
//        this.auditUserService = auditUserService;
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        auditUserService.setCurrentUser(); // Ensure user ID is always set
//        return true;
//    }
//}
