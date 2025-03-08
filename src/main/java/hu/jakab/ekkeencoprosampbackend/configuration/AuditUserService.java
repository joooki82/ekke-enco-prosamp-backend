//package hu.jakab.ekkeencoprosampbackend.configuration;
//
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.stereotype.Service;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.Transactional;
//
//@Service
//public class AuditUserService {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Transactional
//    public void setCurrentUser() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (principal instanceof Jwt jwt) {
//            String userId = jwt.getClaim("sub"); // Keycloak User ID (UUID)
//            entityManager.createNativeQuery("SET LOCAL app.current_user = :userId")
//                         .setParameter("userId", userId)
//                         .executeUpdate();
//        }
//    }
//}
