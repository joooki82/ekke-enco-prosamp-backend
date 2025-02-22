package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.*;
import hu.jakab.ekkeencoprosampbackend.dto.request.LaboratoryRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.LaboratoryResponseDTO;
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
    public ResponseEntity<List<LaboratoryResponseDTO>> getAll() {
        List<LaboratoryResponseDTO> laboratories = service.getAll();
        return laboratories.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(laboratories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaboratoryResponseDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LaboratoryResponseDTO> create(@RequestBody @Valid LaboratoryRequestDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LaboratoryResponseDTO> update(@PathVariable Long id, @RequestBody @Valid LaboratoryRequestDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
