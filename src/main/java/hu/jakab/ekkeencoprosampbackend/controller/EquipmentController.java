package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.equipment.EquipmentCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.equipment.EquipmentRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.equipment.EquipmentResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.EquipmentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accredited_laboratory/equipments")
public class EquipmentController {

    private static final Logger logger = LoggerFactory.getLogger(EquipmentController.class);
    private final EquipmentService service;

    public EquipmentController(EquipmentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EquipmentResponseDTO>> getAllEquipments() {
        logger.info("Fetching all Equipments");
        List<EquipmentResponseDTO> Equipments = service.getAll();
        return Equipments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(Equipments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentResponseDTO> getEquipmentById(@PathVariable Long id) {
        logger.info("Fetching Equipment by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<EquipmentCreatedDTO> createEquipment(@RequestBody @Valid EquipmentRequestDTO dto) {
        logger.info("Creating a new Equipment: {}", dto);
        EquipmentCreatedDTO createdEquipment = service.save(dto);
        return ResponseEntity.status(201).body(createdEquipment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentResponseDTO> updateEquipment(@PathVariable Long id, @RequestBody @Valid EquipmentRequestDTO dto) {
        logger.info("Updating Equipment (ID: {}), New Data: {}", id, dto);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        logger.info("Deleting Equipment with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
