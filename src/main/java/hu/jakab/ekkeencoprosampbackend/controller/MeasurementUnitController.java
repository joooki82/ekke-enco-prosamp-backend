package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.measurementUnit.MeasurementUnitCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.measurementUnit.MeasurementUnitRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.measurementUnit.MeasurementUnitResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.MeasurementUnitService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/measurement-units")
public class MeasurementUnitController {

    private static final Logger logger = LoggerFactory.getLogger(MeasurementUnitController.class);
    private final MeasurementUnitService service;

    public MeasurementUnitController(MeasurementUnitService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MeasurementUnitResponseDTO>> getAllMeasurementUnits() {
        logger.info("Fetching all MeasurementUnits");
        List<MeasurementUnitResponseDTO> MeasurementUnitResponseDTO = service.getAll();
        return MeasurementUnitResponseDTO.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(MeasurementUnitResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeasurementUnitResponseDTO> getMeasurementUnitById(@PathVariable Long id) {
        logger.info("Fetching MeasurementUnit by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<MeasurementUnitCreatedDTO> createMeasurementUnit(@RequestBody @Valid MeasurementUnitRequestDTO dto) {
        logger.info("Creating a new MeasurementUnit: {}", dto);
        MeasurementUnitCreatedDTO createdMeasurementUnit = service.save(dto);
        return ResponseEntity.status(201).body(createdMeasurementUnit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeasurementUnitResponseDTO> updateMeasurementUnit(@PathVariable Long id, @RequestBody @Valid MeasurementUnitRequestDTO dto) {
        logger.info("Updating MeasurementUnit (ID: {}), New Data: {}", id, dto);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeasurementUnit(@PathVariable Long id) {
        logger.info("Deleting MeasurementUnit with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
