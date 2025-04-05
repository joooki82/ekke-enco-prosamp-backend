package hu.jakab.ekkeencoprosampbackend.service;


import hu.jakab.ekkeencoprosampbackend.dto.user.UserDTO;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.UserMapper;
import hu.jakab.ekkeencoprosampbackend.model.Role;
import hu.jakab.ekkeencoprosampbackend.model.User;
import hu.jakab.ekkeencoprosampbackend.model.UserRoleAssignment;
import hu.jakab.ekkeencoprosampbackend.repository.RoleRepository;
import hu.jakab.ekkeencoprosampbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper mapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.roleRepository = roleRepository;
    }

    public List<UserDTO> getAll() {
        logger.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public UserDTO getById(String id) {
        logger.info("Fetching Standard by ID: {}", id);
        return userRepository.findById(UUID.fromString(id))
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
    }

//    public User extractUserFromToken(Authentication authentication) {
//        if (authentication instanceof JwtAuthenticationToken jwtAuthToken) {
//            Map<String, Object> claims = jwtAuthToken.getToken().getClaims();
//            String userId = (String) claims.get("sub");
//            String username = (String) claims.get("preferred_username");
//            String email = (String) claims.get("email");
//            String firstName = (String) claims.get("given_name");
//            String lastName = (String) claims.get("family_name");
//

    /// /            Set<UserRoleAssignment> roleAssignments = jwtAuthToken.getAuthorities().stream()
    /// /                    .map(grantedAuthority -> {
    /// /                        String roleName = grantedAuthority.getAuthority();
    /// /                        Role role = roleRepository.findByRoleName(roleName)
    /// /                                .orElseGet(() -> roleRepository.save(Role.builder().roleName(roleName).build()));
    /// /                        return UserRoleAssignment.builder().role(role).build();
    /// /                    })
    /// /                    .collect(Collectors.toSet());
//
//            Set<UserRoleAssignment> roleAssignments = new HashSet<>();
//
//            Map<String, Object> resourceAccess = (Map<String, Object>) claims.get("resource_access");
//            if (resourceAccess != null) {
//                // Access the specific application roles
//                Map<String, Object> encoProsampUi = (Map<String, Object>) resourceAccess.get("enco-prosamp-angular-ui");
//                if (encoProsampUi != null) {
//                    List<String> roles = (List<String>) encoProsampUi.get("roles");
//                    if (roles != null) {
//                        // Create UserRoleAssignment set based on the extracted roles
//                        roleAssignments = roles.stream()
//                                .map(roleName -> {
//                                    Role role = roleRepository.findByRoleName(roleName)
//                                            .orElseGet(() -> roleRepository.save(Role.builder().roleName(roleName).build()));
//                                    return UserRoleAssignment.builder().role(role).build();
//                                })
//                                .collect(Collectors.toSet());
//
//                        logger.info("Extracted Role Assignments: {}", roleAssignments);
//                    } else {
//                        logger.warn("No roles found in enco-prosamp-angular-ui");
//                    }
//                } else {
//                    logger.warn("No enco-prosamp-angular-ui found in resource_access");
//                }
//            } else {
//                logger.warn("No resource_access found in JWT claims");
//            }
//
//
//            return User.builder()
//                    .id(UUID.fromString(userId))
//                    .username(username)
//                    .email(email)
//                    .firstName(firstName)
//                    .lastName(lastName)
//                    .roleAssignments(roleAssignments)
//                    .build();
//        }
//        throw new
//
//                IllegalArgumentException("Invalid authentication token");
//    }
    /**
     * Extracts user information from the JWT token.
     *
     * @param authentication The authentication object containing the JWT token.
     * @return A User object populated with information from the JWT token.
     */
    public User extractUserFromToken(Authentication authentication) {
        if (!(authentication instanceof JwtAuthenticationToken jwtAuthToken)) {
            throw new IllegalArgumentException("Invalid authentication token");
        }

        Map<String, Object> claims = jwtAuthToken.getToken().getClaims();
        if (claims == null) {
            logger.warn("No claims found in JWT token");
            return null;
        }
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            logger.info("Key: {}, Value: {}", entry.getKey(), entry.getValue());
        }
        String userId = extractClaim(claims, "sub");
        String username = extractClaim(claims, "preferred_username");
        String email = extractClaim(claims, "email");
        String firstName = extractClaim(claims, "given_name");
        String lastName = extractClaim(claims, "family_name");

        Set<UserRoleAssignment> roleAssignments = extractRoleAssignments(claims);

        return User.builder()
                .id(UUID.fromString(userId))
                .username(username)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .roleAssignments(roleAssignments)
                .build();
    }

    private String extractClaim(Map<String, Object> claims, String key) {
        Object value = claims.get(key);
        if (value instanceof String) {
            return (String) value;
        }
        logger.warn("Claim '{}' not found or not a string in JWT claims", key);
        return null;
    }

    private Set<UserRoleAssignment> extractRoleAssignments(Map<String, Object> claims) {
        Map<String, Object> resourceAccess = safeCastToMap(claims.get("resource_access"));
        if (resourceAccess == null) {
            logger.warn("No resource_access found in JWT claims");

            return Collections.emptySet();
        }

        Map<String, Object> encoProsampUi = safeCastToMap(resourceAccess.get("enco-prosamp-angular-ui"));
        if (encoProsampUi == null) {
            logger.warn("No enco-prosamp-angular-ui found in resource_access");
            return Collections.emptySet();
        }

        Object rolesObject = encoProsampUi.get("roles");
        if (rolesObject instanceof List<?>) {
            return extractRoles((List<?>) rolesObject);
        } else {
            logger.warn("Roles not found or not in expected format in enco-prosamp-angular-ui");
            return Collections.emptySet();
        }
    }

    private Set<UserRoleAssignment> extractRoles(List<?> rolesList) {
        return rolesList.stream()
                .filter(role -> role instanceof String)
                .map(role -> (String) role)
                .map(this::getOrCreateRoleAssignment)
                .collect(Collectors.toSet());
    }

    private UserRoleAssignment getOrCreateRoleAssignment(String roleName) {
        Role role = roleRepository.findByRoleName(roleName)
                .orElseGet(() -> roleRepository.save(Role.builder().roleName(roleName).build()));
        return UserRoleAssignment.builder().role(role).build();
    }

    private Map<String, Object> safeCastToMap(Object obj) {
        if (obj instanceof Map<?, ?> map) {
            try {
                return (Map<String, Object>) map;
            } catch (ClassCastException e) {
                logger.warn("Failed to cast to Map<String, Object>: {}", e.getMessage());
            }
        }
        return null;
    }

    @Transactional
    public void saveOrUpdateUser(Authentication authentication) {
        User user = extractUserFromToken(authentication);
        Optional<User> existingUser = userRepository.findById(user.getId());

        if (existingUser.isPresent()) {
            User existing = existingUser.get();
            existing.setUsername(user.getUsername());
            existing.setEmail(user.getEmail());
            existing.setFirstName(user.getFirstName());
            existing.setLastName(user.getLastName());

            // Save the user first to ensure it exists
            userRepository.save(existing);

            // Update roles after user insertion
            existing.getRoleAssignments().clear();
            user.getRoleAssignments().forEach(roleAssignment -> {
                roleAssignment.setUser(existing);
                existing.getRoleAssignments().add(roleAssignment);
            });

            userRepository.save(existing);
        } else {
            // Save the user first to guarantee its existence
            user.getRoleAssignments().forEach(roleAssignment -> roleAssignment.setUser(user));
            userRepository.save(user);
        }
    }

}
