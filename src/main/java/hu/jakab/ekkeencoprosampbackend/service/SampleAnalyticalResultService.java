package hu.jakab.ekkeencoprosampbackend.service;



import hu.jakab.ekkeencoprosampbackend.dto.sampleAnalyticalResult.SampleAnalyticalResultCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sampleAnalyticalResult.SampleAnalyticalResultRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sampleAnalyticalResult.SampleAnalyticalResultResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.SampleAnalyticalResultMapper;
import hu.jakab.ekkeencoprosampbackend.model.*;
import hu.jakab.ekkeencoprosampbackend.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleAnalyticalResultService {

    private static final Logger logger = LoggerFactory.getLogger(SampleAnalyticalResultService.class);

    private final SampleAnalyticalResultRepository repository;
    private final MeasurementUnitRepository measurementUnitRepository;
    private final SampleContaminantRepository sampleContaminantRepository;
    private final AnalyticalLabReportRepository analyticalLabReportRepository;
    private final SampleAnalyticalResultMapper mapper;

    @Autowired
    public SampleAnalyticalResultService(SampleAnalyticalResultRepository repository, MeasurementUnitRepository measurementUnitRepository, SampleContaminantRepository sampleContaminantRepository,
                                         AnalyticalLabReportRepository analyticalLabReportRepository, SampleAnalyticalResultMapper mapper) {
        this.repository = repository;
        this.measurementUnitRepository = measurementUnitRepository;
        this.sampleContaminantRepository = sampleContaminantRepository;
        this.analyticalLabReportRepository = analyticalLabReportRepository;
        this.mapper = mapper;
    }

    public List<SampleAnalyticalResultResponseDTO> getAll() {
        logger.info("Fetching all SampleAnalyticalResults");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public SampleAnalyticalResultResponseDTO getById(Long id) {
        logger.info("Fetching SampleAnalyticalResult by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("SampleAnalyticalResult with ID " + id + " not found"));
    }

    @Transactional
    public SampleAnalyticalResultCreatedDTO save(SampleAnalyticalResultRequestDTO dto) {
        logger.info("Creating a new SampleAnalyticalResult with sampleContaminantId: {}", dto.getSampleContaminantId());
        SampleAnalyticalResult SampleAnalyticalResult = mapper.toEntity(dto);
        try {
            SampleAnalyticalResult savedSampleAnalyticalResult = repository.save(SampleAnalyticalResult);
            return mapper.toCreatedDTO(savedSampleAnalyticalResult);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error saving SampleAnalyticalResult: Duplicate report number");
            logger.error(dto.toString());
            throw new DuplicateResourceException("Failed to create SampleAnalyticalResult: Duplicate report number");
        }
    }

    @Transactional
    public SampleAnalyticalResultResponseDTO update(Long id, SampleAnalyticalResultRequestDTO dto) {
        logger.info("Updating SampleAnalyticalResult (ID: {}) with new details", id);

        // Fetch the existing SampleAnalyticalResult
        SampleAnalyticalResult existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SampleAnalyticalResult with ID " + id + " not found"));

        // Update Lab Report if provided in DTO
        if (dto.getLabReportId() != null) {
            AnalyticalLabReport labReport = analyticalLabReportRepository.findById(dto.getLabReportId())
                    .orElseThrow(() -> new ResourceNotFoundException("LabReport with ID " + dto.getLabReportId() + " not found"));
            existing.setLabReport(labReport);
        }

        // Update other fields if provided in DTO
        if (dto.getSampleContaminantId() != null) {
            SampleContaminant sampleContaminant = sampleContaminantRepository.findById(dto.getSampleContaminantId())
                    .orElseThrow(() -> new ResourceNotFoundException("SampleContaminant with ID " + dto.getSampleContaminantId() + " not found"));
            existing.setSampleContaminant(sampleContaminant);
        }
        if (dto.getResultMain() != null) {
            existing.setResultMain(dto.getResultMain());
        }
        if (dto.getResultControl() != null) {
            existing.setResultControl(dto.getResultControl());
        }
        if (dto.getResultMainControl() != null) {
            existing.setResultMainControl(dto.getResultMainControl());
        }
        if (dto.getMeasurementUnitId() != null) {
            MeasurementUnit measurementUnit = measurementUnitRepository.findById(dto.getMeasurementUnitId())
                    .orElseThrow(() -> new ResourceNotFoundException("MeasurementUnit with ID " + dto.getMeasurementUnitId() + " not found"));
            existing.setMeasurementUnit(measurementUnit);
        }
        if (dto.getDetectionLimit() != null) {
            existing.setDetectionLimit(dto.getDetectionLimit());
        }
        if (dto.getMeasurementUncertainty() != null) {
            existing.setMeasurementUncertainty(dto.getMeasurementUncertainty());
        }
        if (dto.getAnalysisMethod() != null) {
            existing.setAnalysisMethod(dto.getAnalysisMethod());
        }
        if (dto.getAnalysisDate() != null) {
            existing.setAnalysisDate(dto.getAnalysisDate());
        }
        if (dto.getCalculatedConcentration() != null) {
            existing.setCalculatedConcentration(dto.getCalculatedConcentration());
        }

        try {
            SampleAnalyticalResult updatedSampleAnalyticalResult = repository.save(existing);
            return mapper.toResponseDTO(updatedSampleAnalyticalResult);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update SampleAnalyticalResult due to data integrity violation", e);
            throw new DuplicateResourceException("Update failed due to a duplicate or conflicting data entry.");
        } catch (Exception e) {
            logger.error("An unexpected error occurred while updating SampleAnalyticalResult", e);
            throw new RuntimeException("Unexpected error while updating SampleAnalyticalResult.");
        }

    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting SampleAnalyticalResult with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Cannot delete: SampleAnalyticalResult with ID {} not found", id);
            throw new ResourceNotFoundException("SampleAnalyticalResult with ID " + id + " not found");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted SampleAnalyticalResult with ID: {}", id);
    }
}
