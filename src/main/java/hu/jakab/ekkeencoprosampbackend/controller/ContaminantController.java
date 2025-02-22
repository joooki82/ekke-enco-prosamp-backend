package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.*;
import hu.jakab.ekkeencoprosampbackend.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/contaminants")
public class ContaminantController {
    private final ContaminantService service;

    @Autowired
    public ContaminantController(ContaminantService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ContaminantDTO>> getAll() {
        List<ContaminantDTO> contaminants = service.getAll();
        return contaminants.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(contaminants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaminantDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContaminantDTO> create(@RequestBody @Valid ContaminantDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaminantDTO> update(@PathVariable Long id, @RequestBody @Valid ContaminantDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
