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
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.security.config.Elements.JWT;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
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

        // Check if the authentication object is an instance of JwtAuthenticationToken
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
            Jwt jwt = jwtAuth.getToken();

            // Log the JWT claims and headers
            logger.info("JWT Token Value: {}", jwt.getTokenValue());
            logger.info("JWT Claims: {}", jwt.getClaims());

            logger.info("JWT Headers: {}", jwt.getHeaders());

            // Optionally, log specific claims like 'sub' or 'email'
            String subject = jwt.getClaimAsString("sub");
            String email = jwt.getClaimAsString("email");
            logger.info("JWT Subject: {}", subject);
            logger.info("JWT Email: {}", email);
            logger.info("JWT Issuer: {}", jwt.getIssuer());
            logger.info("JWT Audience: {}", jwt.getAudience());
            logger.info("JWT Expiration: {}", jwt.getExpiresAt());
            logger.info("JWT Issued At: {}", jwt.getIssuedAt());

        } else {
            logger.warn("Authentication is not an instance of JwtAuthenticationToken. Actual type: {}", authentication.getClass().getName());
        }

        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
            Jwt jwt = jwtAuth.getToken();

            // Extract the claims map
            Map<String, Object> claims = jwt.getClaims();

            // Extract resource access
            Map<String, Object> resourceAccess = (Map<String, Object>) claims.get("resource_access");
            if (resourceAccess != null) {
                Map<String, Object> encoProsampUi = (Map<String, Object>) resourceAccess.get("enco-prosamp-angular-ui");
                if (encoProsampUi != null) {
                    List<String> roles = (List<String>) encoProsampUi.get("roles");
                    if (roles != null) {
                        // Log the roles
                        logger.info("Extracted Roles: {}", roles);

                        // Save the roles to an array
                        String[] rolesArray = roles.toArray(new String[0]);
                        logger.info("Roles Array: {}", Arrays.toString(rolesArray));

                        // You can now use the rolesArray as needed
                    } else {
                        logger.warn("No roles found in enco-prosamp-angular-ui");
                    }
                } else {
                    logger.warn("No enco-prosamp-angular-ui found in resource_access");
                }
            } else {
                logger.warn("No resource_access found in JWT claims");
            }
        } else {
            logger.warn("Authentication is not an instance of JwtAuthenticationToken. Actual type: {}", authentication.getClass().getName());
        }



        logger.info("Syncing user data for: {}", authentication.getName());
        logger.info("User roles: {}", authentication.getAuthorities());
        logger.info("User details: {}", authentication.getDetails());
        logger.info("User principal: {}", authentication.getPrincipal());
        logger.info("User credentials: {}", authentication.getCredentials());
        logger.info("User name: {}", authentication.getName());
        logger.info("User authentication: {}", authentication);
        logger.info("User is authenticated: {}", authentication.isAuthenticated());
        logger.info("JWT: {}", (authentication));
        service.saveOrUpdateUser(authentication);


        return ResponseEntity.ok().build();
    }



}

