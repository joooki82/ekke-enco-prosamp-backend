package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.SampleMapper;
import hu.jakab.ekkeencoprosampbackend.model.Sample;
import hu.jakab.ekkeencoprosampbackend.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleService {

    private static final Logger logger = LoggerFactory.getLogger(SampleService.class);

    private final SampleRepository repository;
    private final SampleMapper mapper;
    private final SamplingRecordDatM200Repository samplingRecordRepository;
    private final MeasurementUnitRepository measurementUnitRepository;
    private final SamplingTypeRepository samplingTypeRepository;
    private final AdjustmentMethodRepository adjustmentMethodRepository;

    @Autowired
    public SampleService(SampleRepository repository,
                         SampleMapper mapper,
                         SamplingRecordDatM200Repository samplingRecordRepository,
                         MeasurementUnitRepository measurementUnitRepository,
                         SamplingTypeRepository samplingTypeRepository,
                         AdjustmentMethodRepository adjustmentMethodRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.samplingRecordRepository = samplingRecordRepository;
        this.measurementUnitRepository = measurementUnitRepository;
        this.samplingTypeRepository = samplingTypeRepository;
        this.adjustmentMethodRepository = adjustmentMethodRepository;
    }

    public List<SampleResponseDTO> getAll() {
        logger.info("Fetching all samples");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public SampleResponseDTO getById(Long id) {
        logger.info("Fetching sample by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Sample with ID " + id + " not found"));
    }

    @Transactional
    public SampleCreatedDTO save(SampleRequestDTO dto) {
        logger.info("Creating new sample with identifier: '{}'", dto.getSampleIdentifier());
        Sample sample = mapper.toEntity(dto);
        try {
            Sample savedSample = repository.save(sample);
            return mapper.toCreatedDTO(savedSample);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to save sample with identifier '{}': Constraint violation - {}", dto.getSampleIdentifier(), e.getMessage(), e);
            throw new DuplicateResourceException("A sample with identifier '" + dto.getSampleIdentifier() + "' already exists.");
        }
    }

    @Transactional
    public SampleResponseDTO update(Long id, SampleRequestDTO dto) {
        logger.info("Updating sample with ID: {}, new identifier: {}", id, dto.getSampleIdentifier());
        Sample existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sample with ID " + id + " not found."));

        if (dto.getSampleIdentifier() != null) existing.setSampleIdentifier(dto.getSampleIdentifier());
        if (dto.getLocation() != null) existing.setLocation(dto.getLocation());
        if (dto.getEmployeeName() != null) existing.setEmployeeName(dto.getEmployeeName());
        if (dto.getTemperature() != null) existing.setTemperature(dto.getTemperature());
        if (dto.getHumidity() != null) existing.setHumidity(dto.getHumidity());
        if (dto.getPressure() != null) existing.setPressure(dto.getPressure());
        if (dto.getSampleVolumeFlowRate() != null) existing.setSampleVolumeFlowRate(dto.getSampleVolumeFlowRate());
        if (dto.getStartTime() != null) existing.setStartTime(dto.getStartTime());
        if (dto.getEndTime() != null) existing.setEndTime(dto.getEndTime());
        if (dto.getSampleType() != null) existing.setSampleType(dto.getSampleType());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
        if (dto.getRemarks() != null) existing.setRemarks(dto.getRemarks());

        existing.setSamplingRecord(samplingRecordRepository.findById(dto.getSamplingRecordId())
                .orElseThrow(() -> new ResourceNotFoundException("Sampling Record with ID " + dto.getSamplingRecordId() + " not found.")));

        existing.setSampleVolumeFlowRateUnit(measurementUnitRepository.findById(dto.getSampleVolumeFlowRateUnitId())
                .orElseThrow(() -> new ResourceNotFoundException("Measurement Unit with ID " + dto.getSampleVolumeFlowRateUnitId() + " not found")));

        if (dto.getSamplingTypeId() != null) {
            existing.setSamplingType(samplingTypeRepository.findById(dto.getSamplingTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sampling Type with ID " + dto.getSamplingTypeId() + " not found.")));
        }

        if (dto.getAdjustmentMethodId() != null) {
            existing.setAdjustmentMethod(adjustmentMethodRepository.findById(dto.getAdjustmentMethodId())
                    .orElseThrow(() -> new ResourceNotFoundException("Adjustment Method with ID " + dto.getAdjustmentMethodId() + " not found.")));
        }

        try {
            Sample updatedSample = repository.save(existing);
            logger.info("Successfully updated sample with ID: {}", id);
            return mapper.toResponseDTO(updatedSample);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update sample with ID {}, identifier '{}': Constraint violation - {}", id, dto.getSampleIdentifier(), e.getMessage(), e);
            throw new DuplicateResourceException("Update failed: A sample with identifier '" + dto.getSampleIdentifier() + "' already exists.");
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting sample with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Cannot delete: Sample with ID {} not found", id);
            throw new ResourceNotFoundException("Sample with ID " + id + " not found");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted sample with ID: {}", id);
    }

}
