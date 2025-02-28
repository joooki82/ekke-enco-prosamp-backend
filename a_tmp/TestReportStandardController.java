package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.*;
import hu.jakab.ekkeencoprosampbackend.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/test-report-standards")
public class TestReportStandardController {
    private final TestReportStandardService service;

    @Autowired
    public TestReportStandardController(TestReportStandardService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TestReportStandardDTO>> getAll() {
        List<TestReportStandardDTO> standards = service.getAll();
        return standards.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(standards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestReportStandardDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TestReportStandardDTO> create(@RequestBody @Valid TestReportStandardDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestReportStandardDTO> update(@PathVariable Long id, @RequestBody @Valid TestReportStandardDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/assign")
    public ResponseEntity<TestReportStandardDTO> assignStandard(@RequestBody @Valid AssignStandardDTO dto) {
        return ResponseEntity.ok(service.assignStandard(dto));
    }

    @DeleteMapping("/unassign")
    public ResponseEntity<Void> unassignStandard(@RequestBody @Valid AssignStandardDTO dto) {
        return service.unassignStandard(dto) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
