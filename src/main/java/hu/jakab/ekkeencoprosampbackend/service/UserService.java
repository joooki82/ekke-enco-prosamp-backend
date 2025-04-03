package hu.jakab.ekkeencoprosampbackend.service;


import hu.jakab.ekkeencoprosampbackend.dto.standard.StandardCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.standard.StandardRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.standard.StandardResponseDTO;
import hu.jakab.ekkeencoprosampbackend.dto.user.UserDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.StandardMapper;
import hu.jakab.ekkeencoprosampbackend.mapper.UserMapper;
import hu.jakab.ekkeencoprosampbackend.model.Standard;
import hu.jakab.ekkeencoprosampbackend.repository.StandardRepository;
import hu.jakab.ekkeencoprosampbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;
    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<UserDTO> getAll() {
        logger.info("Fetching all users");
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public UserDTO getById(String id) {
        logger.info("Fetching Standard by ID: {}", id);
        return repository.findById(UUID.fromString(id))
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
    }

}
