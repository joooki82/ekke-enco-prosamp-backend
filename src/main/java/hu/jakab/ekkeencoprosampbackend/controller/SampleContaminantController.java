package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.dto.sampleContaminant.*;
import hu.jakab.ekkeencoprosampbackend.service.SampleContaminantService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/sample-contaminants")
public class SampleContaminantController {

    private static final Logger logger = LoggerFactory.getLogger(SampleContaminantController.class);
    private final SampleContaminantService sampleContaminantService;

    public SampleContaminantController(SampleContaminantService sampleContaminantService) {
        this.sampleContaminantService = sampleContaminantService;
    }

    @PostMapping("/link")
    public ResponseEntity<SampleContaminantCreatedDTO> linkSampleToContaminant(
            @RequestBody @Valid SampleContaminantRequestDTO requestDTO) {
        logger.info("Linking Sample ID {} to Contaminant ID {}", requestDTO.getSampleId(), requestDTO.getContaminantId());
        SampleContaminantCreatedDTO sampleContaminant = sampleContaminantService.linkSampleToContaminant(requestDTO);
        return ResponseEntity.status(201).body(sampleContaminant);
    }

    @DeleteMapping("/unlink")
    public ResponseEntity<Void> unlinkSampleFromContaminant(
            @RequestBody @Valid SampleContaminantRequestDTO requestDTO) {
        logger.info("Unlinking Sample ID {} from Contaminant ID {}", requestDTO.getSampleId(), requestDTO.getContaminantId());
        sampleContaminantService.unlinkSampleFromContaminant(requestDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{sampleId}/contaminants")
    public ResponseEntity<SampleWithContaminantsDTO> getContaminantsBySample(@PathVariable Long sampleId) {
        logger.info("Fetching contaminants for Sample ID {}", sampleId);
        SampleWithContaminantsDTO contaminants = sampleContaminantService.getContaminantsBySample(sampleId);
        return ResponseEntity.ok(contaminants);
    }

    @GetMapping("/{sampleId}/samplecontaminants")
    public ResponseEntity<SampleWithSampleContaminantsDTO> getSampleWithSampleContaminantsBySample(@PathVariable Long sampleId) {
        logger.info("Fetching sample with samplecontaminants for Sample ID {}", sampleId);
        SampleWithSampleContaminantsDTO contaminants = sampleContaminantService.getSampleContaminantsBySample(sampleId);
        return ResponseEntity.ok(contaminants);
    }

}
