package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.controller.base.BaseController;
import hu.jakab.ekkeencoprosampbackend.dto.request.AdjustmentMethodRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.AdjustmentMethodResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/adjustment-methods")
public class AdjustmentMethodController extends BaseController<AdjustmentMethodRequestDTO, AdjustmentMethodResponseDTO, Long> {

    private final AdjustmentMethodService service;

    @Autowired
    public AdjustmentMethodController(AdjustmentMethodService service) {
        this.service = service;
    }

    @Override
    public List<AdjustmentMethodResponseDTO> getAllEntities() {
        return service.getAll();
    }

    @Override
    public Optional<AdjustmentMethodResponseDTO> getEntityById(Long id) {
        return service.getById(id);
    }

    @Override
    public AdjustmentMethodResponseDTO createEntity(AdjustmentMethodRequestDTO dto) {
        return service.save(dto);
    }

    @Override
    public Optional<AdjustmentMethodResponseDTO> updateEntity(Long id, AdjustmentMethodRequestDTO dto) {
        return service.update(id, dto);
    }

    @Override
    public boolean deleteEntity(Long id) {
        return service.delete(id);
    }
}


//public class AdjustmentMethodController {
/// /    private final AdjustmentMethodService service;
/// /
/// /    @Autowired
/// /    public AdjustmentMethodController(AdjustmentMethodService service) {
/// /        this.service = service;
/// /    }
/// /
/// /    @GetMapping
/// /    public ResponseEntity<List<AdjustmentMethodResponseDTO>> getAll() {
/// /        List<AdjustmentMethodResponseDTO> methods = service.getAll();
/// /        if (methods.isEmpty()) {
/// /            return ResponseEntity.noContent().build();
/// /        }
/// /        return ResponseEntity.ok(methods);
/// /    }
/// /
/// /    @GetMapping("/{id}")
/// /    public ResponseEntity<AdjustmentMethodResponseDTO> getById(@PathVariable Long id) {
/// /        return service.getById(id)
/// /                .map(ResponseEntity::ok)
/// /                .orElse(ResponseEntity.notFound().build());
/// /    }
/// /
/// /    @PostMapping
/// /    public ResponseEntity<AdjustmentMethodResponseDTO> create(@RequestBody @Valid AdjustmentMethodRequestDTO dto) {
/// /        return ResponseEntity.ok(service.save(dto));
/// /    }
/// /
/// /    @PutMapping("/{id}")
/// /    public ResponseEntity<AdjustmentMethodResponseDTO> update(@PathVariable Long id, @RequestBody @Valid AdjustmentMethodRequestDTO dto) {
/// /        return service.update(id, dto)
/// /                .map(ResponseEntity::ok)
/// /                .orElse(ResponseEntity.notFound().build());
/// /    }
/// /
/// /    @DeleteMapping("/{id}")
/// /    public ResponseEntity<Void> delete(@PathVariable Long id) {
/// /        if (service.delete(id)) {
/// /            return ResponseEntity.noContent().build();
/// /        } else {
/// /            return ResponseEntity.notFound().build();
/// /        }
/// /    }
//}
