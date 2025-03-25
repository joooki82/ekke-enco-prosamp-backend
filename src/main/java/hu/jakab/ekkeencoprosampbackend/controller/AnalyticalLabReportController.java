package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport.AnalyticalLabReportCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport.AnalyticalLabReportRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport.AnalyticalLabReportResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.AnalyticalLabReportService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytical-lab-reports")
public class AnalyticalLabReportController {


    private static final Logger logger = LoggerFactory.getLogger(AnalyticalLabReportController.class);
    private final AnalyticalLabReportService service;

    public AnalyticalLabReportController(AnalyticalLabReportService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AnalyticalLabReportResponseDTO>> getAllAnalyticalLabReports() {
        logger.info("Fetching all AnalyticalLabReports");
        List<AnalyticalLabReportResponseDTO> AnalyticalLabReports = service.getAll();
        return AnalyticalLabReports.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(AnalyticalLabReports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalyticalLabReportResponseDTO> getAnalyticalLabReportById(@PathVariable Long id) {
        logger.info("Fetching AnalyticalLabReport by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<AnalyticalLabReportCreatedDTO> createAnalyticalLabReport(@RequestBody @Valid AnalyticalLabReportRequestDTO dto) {
        logger.info("Creating a new AnalyticalLabReport with report number: {}", dto.getReportNumber());
        AnalyticalLabReportCreatedDTO createdAnalyticalLabReport = service.save(dto);
        return ResponseEntity.status(201).body(createdAnalyticalLabReport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnalyticalLabReportResponseDTO> updateAnalyticalLabReport(@PathVariable Long id, @RequestBody @Valid AnalyticalLabReportRequestDTO dto) {
        logger.info("Updating AnalyticalLabReport (ID: {}) with new details", id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnalyticalLabReport(@PathVariable Long id) {
        logger.info("Deleting AnalyticalLabReport with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
