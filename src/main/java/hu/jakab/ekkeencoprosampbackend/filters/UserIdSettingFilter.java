package hu.jakab.ekkeencoprosampbackend.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class UserIdSettingFilter extends OncePerRequestFilter {

    private final DataSource dataSource;

    public UserIdSettingFilter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String userId = jwt.getClaimAsString("sub");

            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {

                // Set the session variable on every new connection
                statement.execute("SET session.currentUserId = '" + userId + "'");
                logger.info("Set session.currentUserId for user: " + userId);
            } catch (SQLException e) {
                logger.error("Failed to set session.currentUserId in PostgreSQL", e);
            }
        }

        filterChain.doFilter(request, response);
    }
}
