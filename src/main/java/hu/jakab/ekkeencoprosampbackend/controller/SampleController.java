package hu.jakab.ekkeencoprosampbackend.controller;


import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.service.SampleService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/samples")
public class SampleController {

    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
    private final SampleService service;

    public SampleController(SampleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SampleResponseDTO>> getAll() {
        logger.info("Fetching all samples");
        List<SampleResponseDTO> entities = service.getAll();
        return entities.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(entities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SampleResponseDTO> getById(@PathVariable Long id) {
        logger.info("Fetching sample by ID: {}", id);
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Resource with ID " + id + " not found"));
    }

    @PostMapping
    public ResponseEntity<SampleCreatedDTO> create(@RequestBody @Valid SampleRequestDTO dto) {
        logger.info("Creating a new sample");
        SampleCreatedDTO createdEntity = service.save(dto);
        return ResponseEntity.status(201).body(createdEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SampleResponseDTO> update(
            @PathVariable Long id, @RequestBody @Valid SampleRequestDTO dto) {
        logger.info("Updating sample with ID: {}", id);
        return service.update(id, dto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update: Resource with ID " + id + " not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting sample with ID: {}", id);
        boolean deleted = service.delete(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Cannot delete: Resource with ID " + id + " not found.");
        }
        return ResponseEntity.noContent().build();
    }
}
