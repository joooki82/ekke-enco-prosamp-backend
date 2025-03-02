package hu.jakab.ekkeencoprosampbackend.controller;


import hu.jakab.ekkeencoprosampbackend.dto.laboratory.LaboratoryCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.laboratory.LaboratoryRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.laboratory.LaboratoryResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.LaboratoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/laboratories")
public class LaboratoryController {

    private static final Logger logger = LoggerFactory.getLogger(LaboratoryController.class);
    private final LaboratoryService service;

    public LaboratoryController(LaboratoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<LaboratoryResponseDTO>> getAll() {
        logger.info("Fetching all laboratories");
        List<LaboratoryResponseDTO> methods = service.getAll();
        return methods.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(methods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaboratoryResponseDTO> getById(@PathVariable Long id) {
        logger.info("Fetching laboratory by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<LaboratoryCreatedDTO> create(@RequestBody @Valid LaboratoryRequestDTO dto) {
        logger.info("Creating a new laboratory");
        LaboratoryCreatedDTO createdMethod = service.save(dto);
        return ResponseEntity.status(201).body(createdMethod);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LaboratoryResponseDTO> update(
            @PathVariable Long id, @RequestBody @Valid LaboratoryRequestDTO dto) {
        logger.info("Updating laboratory with ID: {}", id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting laboratory with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
