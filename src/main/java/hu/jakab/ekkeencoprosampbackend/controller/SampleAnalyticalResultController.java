package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.sampleAnalyticalResult.SampleAnalyticalResultCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sampleAnalyticalResult.SampleAnalyticalResultRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sampleAnalyticalResult.SampleAnalyticalResultResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.SampleAnalyticalResultService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/sample-analytical-results")
public class SampleAnalyticalResultController {


    private static final Logger logger = LoggerFactory.getLogger(SampleAnalyticalResultController.class);
    private final SampleAnalyticalResultService service;

    public SampleAnalyticalResultController(SampleAnalyticalResultService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SampleAnalyticalResultResponseDTO>> getAllSampleAnalyticalResults() {
        logger.info("Fetching all SampleAnalyticalResults");
        List<SampleAnalyticalResultResponseDTO> sampleAnalyticalResults = service.getAll();
        return ResponseEntity.ok(sampleAnalyticalResults != null ? sampleAnalyticalResults : Collections.emptyList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SampleAnalyticalResultResponseDTO> getSampleAnalyticalResultById(@PathVariable Long id) {
        logger.info("Fetching SampleAnalyticalResult by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<SampleAnalyticalResultCreatedDTO> createSampleAnalyticalResult(@RequestBody @Valid SampleAnalyticalResultRequestDTO dto) {
        logger.info("Creating a new SampleAnalyticalResult with SampleContaminantId: {}", dto.getSampleContaminantId());
        SampleAnalyticalResultCreatedDTO createdSampleAnalyticalResult = service.save(dto);
        return ResponseEntity.status(201).body(createdSampleAnalyticalResult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SampleAnalyticalResultResponseDTO> updateSampleAnalyticalResult(@PathVariable Long id, @RequestBody @Valid SampleAnalyticalResultRequestDTO dto) {
        logger.info("Updating SampleAnalyticalResult (ID: {}) with new details", id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSampleAnalyticalResult(@PathVariable Long id) {
        logger.info("Deleting SampleAnalyticalResult with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-sample-contaminant/{sampleContaminantId}")
    public ResponseEntity<SampleAnalyticalResultResponseDTO> getSampleAnalyticalResultBySampleContaminantId(@PathVariable Long sampleContaminantId) {
        logger.info("Fetching SampleAnalyticalResult by sampleContaminantId: {}", sampleContaminantId);
        return ResponseEntity.ok(service.getBySampleContaminantId(sampleContaminantId));
    }
}
