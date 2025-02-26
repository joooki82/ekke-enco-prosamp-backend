package hu.jakab.ekkeencoprosampbackend.controller.base;

import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public abstract class BaseController<REQ, RES, RES_CREATE, ID>
        implements ReadController<RES, ID>,
        CreateController<REQ, RES_CREATE>,
        UpdateController<REQ, RES, ID>,
        DeleteController<ID> {

    @GetMapping
    public ResponseEntity<List<RES>> getAll() {
        List<RES> entities = getAllEntities();
        return entities.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(entities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RES> getById(@PathVariable ID id) {
        return getEntityById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Resource with ID " + id + " not found"));
    }

    @PostMapping
    public ResponseEntity<RES_CREATE> create(@RequestBody @Valid REQ dto) {
        RES_CREATE createdEntity = createEntity(dto);
        return ResponseEntity.status(201).body(createdEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RES> update(@PathVariable ID id, @RequestBody @Valid REQ dto) {
        return updateEntity(id, dto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update: Resource with ID " + id + " not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        boolean deleted = deleteEntity(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Cannot delete: Resource with ID " + id + " not found.");
        }
        return ResponseEntity.noContent().build();
    }

}
