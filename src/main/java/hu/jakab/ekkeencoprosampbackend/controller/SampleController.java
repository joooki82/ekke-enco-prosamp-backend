package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.model.*;
import hu.jakab.ekkeencoprosampbackend.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/samples")
public class SampleController {
    private final SampleService service;

    @Autowired
    public SampleController(SampleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SampleDTO>> getAll() {
        List<SampleDTO> samples = service.getAll();
        if (samples.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(samples);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SampleDTO> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SampleDTO> create(@RequestBody @Valid SampleDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SampleDTO> update(@PathVariable Long id, @RequestBody @Valid SampleDTO dto) {
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
