package hu.jakab.ekkeencoprosampbackend.controller;


import hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200.SamplingRecordDatM200CreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200.SamplingRecordDatM200RequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200.SamplingRecordDatM200ResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.SamplingRecordDatM200Service;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sampling-record-datm200")
public class SamplingRecordDatM200Controller {

    private static final Logger logger = LoggerFactory.getLogger(SamplingRecordDatM200Controller.class);
    private final SamplingRecordDatM200Service service;

    public SamplingRecordDatM200Controller(SamplingRecordDatM200Service service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SamplingRecordDatM200ResponseDTO>> getAllSamplingRecordDatM200s() {
        logger.info("Fetching all SamplingRecordDatM200s");
        List<SamplingRecordDatM200ResponseDTO> SamplingRecordDatM200s = service.getAll();
        return SamplingRecordDatM200s.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(SamplingRecordDatM200s);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SamplingRecordDatM200ResponseDTO> getSamplingRecordDatM200ById(@PathVariable Long id) {
        logger.info("Fetching SamplingRecordDatM200 by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<SamplingRecordDatM200CreatedDTO> createSamplingRecordDatM200(@RequestBody @Valid SamplingRecordDatM200RequestDTO dto) {
        logger.info("Creating a new SamplingRecordDatM200: {}", dto);
        SamplingRecordDatM200CreatedDTO createdSamplingRecordDatM200 = service.save(dto);
        return ResponseEntity.status(201).body(createdSamplingRecordDatM200);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SamplingRecordDatM200ResponseDTO> updateSamplingRecordDatM200(@PathVariable Long id, @RequestBody @Valid SamplingRecordDatM200RequestDTO dto) {
        logger.info("Updating SamplingRecordDatM200 with ID: {}, New Data: {}", id, dto);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSamplingRecordDatM200(@PathVariable Long id) {
        logger.info("Deleting SamplingRecordDatM200 with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
