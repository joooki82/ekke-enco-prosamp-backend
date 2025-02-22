package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.*;
import hu.jakab.ekkeencoprosampbackend.dto.request.ContaminantGroupRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.ContaminantGroupResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/contaminant-groups")
public class ContaminantGroupController {
    private final ContaminantGroupService service;

    @Autowired
    public ContaminantGroupController(ContaminantGroupService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ContaminantGroupResponseDTO>> getAll() {
        List<ContaminantGroupResponseDTO> groups = service.getAll();
        return groups.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(groups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaminantGroupResponseDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContaminantGroupResponseDTO> create(@RequestBody @Valid ContaminantGroupRequestDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaminantGroupResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ContaminantGroupRequestDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
