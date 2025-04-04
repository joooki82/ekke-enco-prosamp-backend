package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.ContaminantService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accredited_laboratory/contaminants")
public class ContaminantController {

    private static final Logger logger = LoggerFactory.getLogger(ContaminantController.class);
    private final ContaminantService service;

    public ContaminantController(ContaminantService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ContaminantResponseDTO>> getAllContaminants() {
        logger.info("Fetching all Contaminants");
        List<ContaminantResponseDTO> Contaminants = service.getAll();
        return Contaminants.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(Contaminants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaminantResponseDTO> getContaminantById(@PathVariable Long id) {
        logger.info("Fetching Contaminant by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ContaminantCreatedDTO> createContaminant(@RequestBody @Valid ContaminantRequestDTO dto) {
        logger.info("Creating a new Contaminant: {}", dto);
        ContaminantCreatedDTO createdContaminant = service.save(dto);
        return ResponseEntity.status(201).body(createdContaminant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaminantResponseDTO> updateContaminant(@PathVariable Long id, @RequestBody @Valid ContaminantRequestDTO dto) {
        logger.info("Updating Contaminant (ID: {}), New Data: {}", id, dto);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContaminant(@PathVariable Long id) {
        logger.info("Deleting Contaminant with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
