package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.contaminantGroup.ContaminantGroupCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.contaminantGroup.ContaminantGroupRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.contaminantGroup.ContaminantGroupResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.ContaminantGroupService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/accredited_laboratory/contaminant-groups")
public class ContaminantGroupController {

    private static final Logger logger = LoggerFactory.getLogger(ContaminantGroupController.class);
    private final ContaminantGroupService service;

    public ContaminantGroupController(ContaminantGroupService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ContaminantGroupResponseDTO>> getAllContaminantGroups() {
        logger.info("Fetching all ContaminantGroups");
        List<ContaminantGroupResponseDTO> contaminantGroups = service.getAll();
        return ResponseEntity.ok(contaminantGroups != null ? contaminantGroups : Collections.emptyList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaminantGroupResponseDTO> getContaminantGroupById(@PathVariable Long id) {
        logger.info("Fetching ContaminantGroup by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ContaminantGroupCreatedDTO> createContaminantGroup(@RequestBody @Valid ContaminantGroupRequestDTO dto) {
        logger.info("Creating a new ContaminantGroup: {}", dto);
        ContaminantGroupCreatedDTO createdContaminantGroup = service.save(dto);
        return ResponseEntity.status(201).body(createdContaminantGroup);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaminantGroupResponseDTO> updateContaminantGroup(@PathVariable Long id, @RequestBody @Valid ContaminantGroupRequestDTO dto) {
        logger.info("Updating ContaminantGroup (ID: {}), New Data: {}", id, dto);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContaminantGroup(@PathVariable Long id) {
        logger.info("Deleting ContaminantGroup with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
