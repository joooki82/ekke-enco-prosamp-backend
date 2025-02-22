package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.request.SampleRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.SampleResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.*;
import hu.jakab.ekkeencoprosampbackend.service.*;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<SampleResponseDTO>> getAll() {
        List<SampleResponseDTO> samples = service.getAll();
        if (samples.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(samples);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SampleResponseDTO> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SampleResponseDTO> create(@RequestBody @Valid SampleRequestDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SampleResponseDTO> update(@PathVariable Long id, @RequestBody @Valid SampleRequestDTO dto) {
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
