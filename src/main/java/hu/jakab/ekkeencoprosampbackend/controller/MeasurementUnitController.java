package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.*;
import hu.jakab.ekkeencoprosampbackend.dto.request.MeasurementUnitRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.MeasurementUnitResponseDTO;
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
    public ResponseEntity<List<MeasurementUnitResponseDTO>> getAll() {
        List<MeasurementUnitResponseDTO> units = service.getAll();
        return units.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(units);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeasurementUnitResponseDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MeasurementUnitResponseDTO> create(@RequestBody @Valid MeasurementUnitRequestDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeasurementUnitResponseDTO> update(@PathVariable Long id, @RequestBody @Valid MeasurementUnitRequestDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
