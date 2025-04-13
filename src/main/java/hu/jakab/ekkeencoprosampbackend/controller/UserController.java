package hu.jakab.ekkeencoprosampbackend.controller;


import hu.jakab.ekkeencoprosampbackend.dto.user.UserDTO;
import hu.jakab.ekkeencoprosampbackend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService service;

    public UserController(UserService service ) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        logger.info("Fetching all users");
        List<UserDTO> users = service.getAll();
        return ResponseEntity.ok(users != null ? users : Collections.emptyList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        logger.info("Fetching User by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/sync")
    public ResponseEntity<Void> syncUserData(Authentication authentication) {
        service.saveOrUpdateUser(authentication);
        return ResponseEntity.ok().build();
    }


}
