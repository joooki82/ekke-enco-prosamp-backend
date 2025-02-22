package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.*;
import hu.jakab.ekkeencoprosampbackend.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sampling-records-datm200")
public class SamplingRecordDatM200Controller {
    private final SamplingRecordDatM200Service service;

    @Autowired
    public SamplingRecordDatM200Controller(SamplingRecordDatM200Service service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SamplingRecordDatM200DTO>> getAll() {
        List<SamplingRecordDatM200DTO> records = service.getAll();
        return records.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(records);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SamplingRecordDatM200DTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SamplingRecordDatM200DTO> create(@RequestBody @Valid SamplingRecordDatM200DTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SamplingRecordDatM200DTO> update(@PathVariable Long id, @RequestBody @Valid SamplingRecordDatM200DTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
