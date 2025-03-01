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
    public ResponseEntity<List<SampleResponseDTO>> getAllSamples() {
        logger.info("Fetching all samples");
        List<SampleResponseDTO> samples = service.getAll();
        return samples.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(samples);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SampleResponseDTO> getSampleById(@PathVariable Long id) {
        logger.info("Fetching sample by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<SampleCreatedDTO> createSample(@RequestBody @Valid SampleRequestDTO dto) {
        logger.info("Creating a new sample: {}", dto);
        SampleCreatedDTO createdSample = service.save(dto);
        return ResponseEntity.status(201).body(createdSample);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SampleResponseDTO> updateSample(@PathVariable Long id, @RequestBody @Valid SampleRequestDTO dto) {
        logger.info("Updating sample with ID: {}, New Data: {}", id, dto);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSample(@PathVariable Long id) {
        logger.info("Deleting sample with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
