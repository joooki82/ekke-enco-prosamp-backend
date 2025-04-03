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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


}

