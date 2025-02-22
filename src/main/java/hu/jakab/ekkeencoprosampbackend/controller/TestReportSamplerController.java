package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.*;
import hu.jakab.ekkeencoprosampbackend.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/test-report-samplers")
public class TestReportSamplerController {
    private final TestReportSamplerService service;

    @Autowired
    public TestReportSamplerController(TestReportSamplerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TestReportSamplerDTO>> getAll() {
        List<TestReportSamplerDTO> samplers = service.getAll();
        return samplers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(samplers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestReportSamplerDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TestReportSamplerDTO> create(@RequestBody @Valid TestReportSamplerDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestReportSamplerDTO> update(@PathVariable Long id, @RequestBody @Valid TestReportSamplerDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/assign")
    public ResponseEntity<TestReportSamplerDTO> assignSampler(@RequestBody @Valid AssignSamplerDTO dto) {
        return ResponseEntity.ok(service.assignSampler(dto));
    }

    @DeleteMapping("/unassign")
    public ResponseEntity<Void> unassignSampler(@RequestBody @Valid AssignSamplerDTO dto) {
        return service.unassignSampler(dto) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
