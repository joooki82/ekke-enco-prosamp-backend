package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.service.AdjustmentMethodService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adjustment-methods")
public class AdjustmentMethodController {

    private static final Logger logger = LoggerFactory.getLogger(AdjustmentMethodController.class);
    private final AdjustmentMethodService service;

    public AdjustmentMethodController(AdjustmentMethodService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AdjustmentMethodResponseDTO>> getAll() {
        logger.info("Fetching all adjustment methods");
        List<AdjustmentMethodResponseDTO> entities = service.getAll();
        return entities.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(entities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdjustmentMethodResponseDTO> getById(@PathVariable Long id) {
        logger.info("Fetching adjustment method by ID: {}", id);
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Resource with ID " + id + " not found"));
    }

    @PostMapping
    public ResponseEntity<AdjustmentMethodCreatedDTO> create(@RequestBody @Valid AdjustmentMethodRequestDTO dto) {
        logger.info("Creating a new adjustment method");
        AdjustmentMethodCreatedDTO createdEntity = service.save(dto);
        return ResponseEntity.status(201).body(createdEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdjustmentMethodResponseDTO> update(
            @PathVariable Long id, @RequestBody @Valid AdjustmentMethodRequestDTO dto) {
        logger.info("Updating adjustment method with ID: {}", id);
        return service.update(id, dto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update: Resource with ID " + id + " not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting adjustment method with ID: {}", id);
        boolean deleted = service.delete(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Cannot delete: Resource with ID " + id + " not found.");
        }
        return ResponseEntity.noContent().build();
    }
}
