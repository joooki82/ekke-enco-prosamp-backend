package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.*;
import hu.jakab.ekkeencoprosampbackend.dto.request.AnalyticalLabReportRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.AnalyticalLabReportResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/analytical-lab-reports")
public class AnalyticalLabReportController {
    private final AnalyticalLabReportService service;

    @Autowired
    public AnalyticalLabReportController(AnalyticalLabReportService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AnalyticalLabReportResponseDTO>> getAll() {
        List<AnalyticalLabReportResponseDTO> reports = service.getAll();
        return reports.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalyticalLabReportResponseDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AnalyticalLabReportResponseDTO> create(@RequestBody @Valid AnalyticalLabReportRequestDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnalyticalLabReportResponseDTO> update(@PathVariable Long id, @RequestBody @Valid AnalyticalLabReportRequestDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
