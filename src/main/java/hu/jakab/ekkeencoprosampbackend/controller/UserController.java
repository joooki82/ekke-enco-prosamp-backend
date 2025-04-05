package hu.jakab.ekkeencoprosampbackend.controller;


import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportResponseDTO;
import hu.jakab.ekkeencoprosampbackend.dto.user.UserDTO;
import hu.jakab.ekkeencoprosampbackend.service.TestReportService;
import hu.jakab.ekkeencoprosampbackend.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.security.config.Elements.JWT;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService service;
    private final DataSource dataSource;

    public UserController(UserService service, DataSource dataSource) {
        this.service = service;
        this.dataSource = dataSource;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        logger.info("Fetching all users");
        List<UserDTO> Users = service.getAll();
        return Users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(Users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        logger.info("Fetching User by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/sync")
    public ResponseEntity<Void> syncUserData(Authentication authentication) {

//        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
//            Jwt jwt = (Jwt) authentication.getPrincipal();
//            String userId = jwt.getClaimAsString("sub"); // Assuming "sub" claim holds the user ID
//
//            try (Connection connection = dataSource.getConnection();
//                 Statement statement = connection.createStatement()) {
//
//
//                statement.execute("SET session.currentUserId = '" + userId + "'");
//                logger.info("Set current user ID in PostgreSQL session: " + userId);
//                logger.info("STATEMENT" + statement);
//            } catch (SQLException e) {
//                logger.error("Failed to set user ID in PostgreSQL session", e);
//            }
//        }
        service.saveOrUpdateUser(authentication);
        return ResponseEntity.ok().build();
    }



}

