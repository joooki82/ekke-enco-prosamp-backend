package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.*;
import hu.jakab.ekkeencoprosampbackend.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sampling-types")
public class SamplingTypeController {
    private final SamplingTypeService service;

    @Autowired
    public SamplingTypeController(SamplingTypeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SamplingTypeDTO>> getAll() {
        List<SamplingTypeDTO> samplingTypes = service.getAll();
        return samplingTypes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(samplingTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SamplingTypeDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SamplingTypeDTO> create(@RequestBody @Valid SamplingTypeDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SamplingTypeDTO> update(@PathVariable Long id, @RequestBody @Valid SamplingTypeDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
