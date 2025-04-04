package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.AdjustmentMethodService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accredited_laboratory/adjustment-methods")
public class AdjustmentMethodController {

    private static final Logger logger = LoggerFactory.getLogger(AdjustmentMethodController.class);
    private final AdjustmentMethodService service;

    public AdjustmentMethodController(AdjustmentMethodService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AdjustmentMethodResponseDTO>> getAll() {
        logger.info("Fetching all adjustment methods");
        List<AdjustmentMethodResponseDTO> methods = service.getAll();
        return methods.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(methods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdjustmentMethodResponseDTO> getById(@PathVariable Long id) {
        logger.info("Fetching adjustment method by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<AdjustmentMethodCreatedDTO> create(@RequestBody @Valid AdjustmentMethodRequestDTO dto) {
        logger.info("Creating a new adjustment method");
        AdjustmentMethodCreatedDTO createdMethod = service.save(dto);
        return ResponseEntity.status(201).body(createdMethod);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdjustmentMethodResponseDTO> update(
            @PathVariable Long id, @RequestBody @Valid AdjustmentMethodRequestDTO dto) {
        logger.info("Updating adjustment method with ID: {}", id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting adjustment method with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
