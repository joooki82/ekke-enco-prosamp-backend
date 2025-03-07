package hu.jakab.ekkeencoprosampbackend.controller;


import hu.jakab.ekkeencoprosampbackend.dto.standard.StandardCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.standard.StandardRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.standard.StandardResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.StandardService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/standard")
public class StandardController {

    private static final Logger logger = LoggerFactory.getLogger(StandardController.class);
    private final StandardService service;

    public StandardController(StandardService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<StandardResponseDTO>> getAllStandards() {
        logger.info("Fetching all Standards");
        List<StandardResponseDTO> Standards = service.getAll();
        return Standards.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(Standards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> getStandardById(@PathVariable Long id) {
        logger.info("Fetching Standard by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<StandardCreatedDTO> createStandard(@RequestBody @Valid StandardRequestDTO dto) {
        logger.info("Creating a new Standard: {}", dto);
        StandardCreatedDTO createdStandard = service.save(dto);
        return ResponseEntity.status(201).body(createdStandard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> updateStandard(@PathVariable Long id, @RequestBody @Valid StandardRequestDTO dto) {
        logger.info("Updating Standard (ID: {}), New Data: {}", id, dto);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStandard(@PathVariable Long id) {
        logger.info("Deleting Standard with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
