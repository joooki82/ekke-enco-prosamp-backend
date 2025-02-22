package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.model.*;
import hu.jakab.ekkeencoprosampbackend.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/adjustment-methods")
public class AdjustmentMethodController {
    private final AdjustmentMethodService service;

    @Autowired
    public AdjustmentMethodController(AdjustmentMethodService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AdjustmentMethodDTO>> getAll() {
        List<AdjustmentMethodDTO> methods = service.getAll();
        if (methods.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(methods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdjustmentMethodDTO> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AdjustmentMethodDTO> create(@RequestBody @Valid AdjustmentMethodDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdjustmentMethodDTO> update(@PathVariable Long id, @RequestBody @Valid AdjustmentMethodDTO dto) {
        return service.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
