package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.*;
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
    public ResponseEntity<List<ContaminantGroupDTO>> getAll() {
        List<ContaminantGroupDTO> groups = service.getAll();
        return groups.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(groups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaminantGroupDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContaminantGroupDTO> create(@RequestBody @Valid ContaminantGroupDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaminantGroupDTO> update(@PathVariable Long id, @RequestBody @Valid ContaminantGroupDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
