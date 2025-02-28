package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.*;
import hu.jakab.ekkeencoprosampbackend.dto.request.StandardRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.StandardResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/standards")
public class StandardController {
    private final StandardService service;

    @Autowired
    public StandardController(StandardService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<StandardResponseDTO>> getAll() {
        List<StandardResponseDTO> standards = service.getAll();
        return standards.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(standards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StandardResponseDTO> create(@RequestBody @Valid StandardRequestDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> update(@PathVariable Long id, @RequestBody @Valid StandardRequestDTO  dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
