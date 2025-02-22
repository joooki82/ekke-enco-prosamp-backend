package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.*;
import hu.jakab.ekkeencoprosampbackend.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sampling-record-equipment")
public class SamplingRecordEquipmentController {
    private final SamplingRecordEquipmentService service;

    @Autowired
    public SamplingRecordEquipmentController(SamplingRecordEquipmentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SamplingRecordEquipmentDTO>> getAll() {
        List<SamplingRecordEquipmentDTO> records = service.getAll();
        return records.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(records);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SamplingRecordEquipmentDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SamplingRecordEquipmentDTO> create(@RequestBody @Valid SamplingRecordEquipmentDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SamplingRecordEquipmentDTO> update(@PathVariable Long id, @RequestBody @Valid SamplingRecordEquipmentDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/assign")
    public ResponseEntity<SamplingRecordEquipmentDTO> assignEquipment(@RequestBody @Valid AssignEquipmentDTO dto) {
        return ResponseEntity.ok(service.assignEquipment(dto));
    }

    @DeleteMapping("/unassign")
    public ResponseEntity<Void> unassignEquipment(@RequestBody @Valid AssignEquipmentDTO dto) {
        return service.unassignEquipment(dto) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
