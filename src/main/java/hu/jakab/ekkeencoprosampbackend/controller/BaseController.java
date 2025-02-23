package hu.jakab.ekkeencoprosampbackend.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
public abstract class BaseController<REQ, RES, ID> {

    protected abstract List<RES> getAllEntities();
    protected abstract Optional<RES> getEntityById(ID id);
    protected abstract RES createEntity(REQ dto);
    protected abstract Optional<RES> updateEntity(ID id, REQ dto);
    protected abstract boolean deleteEntity(ID id);

    @GetMapping
    public ResponseEntity<List<RES>> getAll() {
        List<RES> entities = getAllEntities();
        return entities.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(entities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RES> getById(@PathVariable ID id) {
        return getEntityById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RES> create(@RequestBody @Valid REQ dto) {
        return ResponseEntity.ok(createEntity(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RES> update(@PathVariable ID id, @RequestBody @Valid REQ dto) {
        return updateEntity(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        return deleteEntity(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
