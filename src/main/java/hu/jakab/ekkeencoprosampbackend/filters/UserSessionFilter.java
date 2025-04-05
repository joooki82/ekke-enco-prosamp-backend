//package hu.jakab.ekkeencoprosampbackend.filters;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class UserSessionFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        Authentication authentication = (Authentication) request.getUserPrincipal();
//
//        if (authentication instanceof JwtAuthenticationToken jwtAuthToken) {
//            String userId = jwtAuthToken.getToken().getClaimAsString("sub");
//            if (userId != null) {
//                setCurrentUserInSession(userId);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private void setCurrentUserInSession(String userId) {
//        // Properly set the current user in the database session
//        String safeUserId = userId.replace("'", "''");
//        String sql = "SET LOCAL app.current_user = '" + safeUserId + "'";
//        jdbcTemplate.execute(sql);
//        System.out.println("Set current user ID in session: " + safeUserId);
//    }
//}
