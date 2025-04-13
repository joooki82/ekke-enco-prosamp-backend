package hu.jakab.ekkeencoprosampbackend.controller;


import hu.jakab.ekkeencoprosampbackend.dto.samplingType.SamplingTypeCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingType.SamplingTypeRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingType.SamplingTypeResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.SamplingTypeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accredited_laboratory/sampling-types")
public class SamplingTypeController {

    private static final Logger logger = LoggerFactory.getLogger(SamplingTypeController.class);
    private final SamplingTypeService service;

    public SamplingTypeController(SamplingTypeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SamplingTypeResponseDTO>> getAllSamplingTypes() {
        logger.info("Fetching all SamplingTypes");
        List<SamplingTypeResponseDTO> SamplingTypes = service.getAll();
        return SamplingTypes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(SamplingTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SamplingTypeResponseDTO> getSamplingTypeById(@PathVariable Long id) {
        logger.info("Fetching SamplingType by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<SamplingTypeCreatedDTO> createSamplingType(@RequestBody @Valid SamplingTypeRequestDTO dto) {
        logger.info("Creating a new SamplingType: {}", dto);
        SamplingTypeCreatedDTO createdSamplingType = service.save(dto);
        return ResponseEntity.status(201).body(createdSamplingType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SamplingTypeResponseDTO> updateSamplingType(@PathVariable Long id, @RequestBody @Valid SamplingTypeRequestDTO dto) {
        logger.info("Updating SamplingType (ID: {}), New Data: {}", id, dto);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSamplingType(@PathVariable Long id) {
        logger.info("Deleting SamplingType with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
