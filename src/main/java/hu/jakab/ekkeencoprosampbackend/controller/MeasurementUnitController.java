package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.*;
import hu.jakab.ekkeencoprosampbackend.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/measurement-units")
public class MeasurementUnitController {
    private final MeasurementUnitService service;

    @Autowired
    public MeasurementUnitController(MeasurementUnitService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MeasurementUnitDTO>> getAll() {
        List<MeasurementUnitDTO> units = service.getAll();
        return units.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(units);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeasurementUnitDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MeasurementUnitDTO> create(@RequestBody @Valid MeasurementUnitDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeasurementUnitDTO> update(@PathVariable Long id, @RequestBody @Valid MeasurementUnitDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
