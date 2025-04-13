package hu.jakab.ekkeencoprosampbackend.controller;


import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.TestReportService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testreports")
public class TestReportController {

    private static final Logger logger = LoggerFactory.getLogger(TestReportController.class);
    private final TestReportService service;

    public TestReportController(TestReportService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TestReportResponseDTO>> getAllTestReports() {
        logger.info("Fetching all TestReports");
        List<TestReportResponseDTO> TestReports = service.getAll();
        return TestReports.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(TestReports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestReportResponseDTO> getTestReportById(@PathVariable Long id) {
        logger.info("Fetching TestReport by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<TestReportCreatedDTO> createTestReport(@RequestBody @Valid TestReportRequestDTO dto) {
        logger.info("Creating a new TestReport: {}", dto);
        TestReportCreatedDTO createdTestReport = service.save(dto);
        return ResponseEntity.status(201).body(createdTestReport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestReportResponseDTO> updateTestReport(@PathVariable Long id, @RequestBody @Valid TestReportRequestDTO dto) {
        logger.info("Updating TestReport (ID: {}), New Data: {}", id, dto);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestReport(@PathVariable Long id) {
        logger.info("Deleting TestReport with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/generate")
    public ResponseEntity<byte[]> generateReport(@PathVariable Long id) {
        byte[] pdfBytes = service.generateReport(id);
        logger.info("Generating PDF report for TestReport with ID: {}", id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

}
