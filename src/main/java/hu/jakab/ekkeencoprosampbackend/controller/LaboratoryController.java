package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.*;
import hu.jakab.ekkeencoprosampbackend.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/laboratories")
public class LaboratoryController {
    private final LaboratoryService service;

    @Autowired
    public LaboratoryController(LaboratoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<LaboratoryDTO>> getAll() {
        List<LaboratoryDTO> laboratories = service.getAll();
        return laboratories.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(laboratories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaboratoryDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LaboratoryDTO> create(@RequestBody @Valid LaboratoryDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LaboratoryDTO> update(@PathVariable Long id, @RequestBody @Valid LaboratoryDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
