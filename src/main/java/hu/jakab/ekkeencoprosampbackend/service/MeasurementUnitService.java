package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.measurementUnit.MeasurementUnitCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.measurementUnit.MeasurementUnitRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.measurementUnit.MeasurementUnitResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.MeasurementUnitMapper;
import hu.jakab.ekkeencoprosampbackend.model.MeasurementUnit;
import hu.jakab.ekkeencoprosampbackend.repository.MeasurementUnitRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementUnitService {
    private static final Logger logger = LoggerFactory.getLogger(MeasurementUnitService.class);

    private final MeasurementUnitRepository repository;
    private final MeasurementUnitMapper mapper;

    @Autowired
    public MeasurementUnitService(MeasurementUnitRepository repository, MeasurementUnitMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<MeasurementUnitResponseDTO> getAll() {
        logger.info("Fetching all MeasurementUnits");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public MeasurementUnitResponseDTO getById(Long id) {
        logger.info("Fetching MeasurementUnit by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("MeasurementUnit with ID " + id + " not found"));
    }

    @Transactional
    public MeasurementUnitCreatedDTO save(MeasurementUnitRequestDTO dto) {
        logger.info("Creating new measurement unit with code: '{}'", dto.getUnitCode());

        MeasurementUnit measurementUnit = mapper.toEntity(dto);

        if (dto.getBaseUnitId() != null) {
            MeasurementUnit baseUnit = repository.findById(dto.getBaseUnitId())
                    .orElseThrow(() -> {
                        logger.warn("Base unit with ID {} not found for new measurement unit", dto.getBaseUnitId());
                        return new ResourceNotFoundException("Base unit with ID " + dto.getBaseUnitId() + " not found.");
                    });
            measurementUnit.setBaseUnit(baseUnit);
        }

        try {
            MeasurementUnit savedMeasurementUnit = repository.save(measurementUnit);
            return mapper.toCreatedDTO(savedMeasurementUnit);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to save measurement unit with code '{}': Constraint violation - {}", dto.getUnitCode(), e.getMessage(), e);
            throw new DuplicateResourceException("A measurement unit with code '" + dto.getUnitCode() + "' already exists.");
        }
    }

    @Transactional
    public MeasurementUnitResponseDTO update(Long id, MeasurementUnitRequestDTO dto) {
        logger.info("Updating measurement unit with ID: {}", id);
        
        MeasurementUnit existing = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Update failed: Measurement unit with ID {} not found", id);
                    return new ResourceNotFoundException("Measurement unit with ID " + id + " not found.");
                });

        if (dto.getUnitCode() != null) existing.setUnitCode(dto.getUnitCode());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getUnitCategory() != null) existing.setUnitCategory(dto.getUnitCategory());
        if (dto.getConversionFactor() != null) existing.setConversionFactor(dto.getConversionFactor());
        if (dto.getStandardBody() != null) existing.setStandardBody(dto.getStandardBody());

        if (dto.getBaseUnitId() != null) {
            MeasurementUnit baseUnit = repository.findById(dto.getBaseUnitId())
                    .orElseThrow(() -> {
                        logger.warn("Base unit with ID {} not found during update", dto.getBaseUnitId());
                        return new ResourceNotFoundException("Base unit with ID " + dto.getBaseUnitId() + " not found.");
                    });
            existing.setBaseUnit(baseUnit);
        }

        try {
            MeasurementUnit updatedMeasurementUnit = repository.save(existing);
            logger.info("Successfully updated MeasurementUnit (ID: {})", id);
            return mapper.toResponseDTO(updatedMeasurementUnit);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update measurement unit with ID {}, code '{}': Constraint violation - {}", id, dto.getUnitCode(), e.getMessage(), e);
            throw new DuplicateResourceException("Update failed: A measurement unit with code '" + dto.getUnitCode() + "' already exists.");
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting MeasurementUnit with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Cannot delete: MeasurementUnit with ID {} not found", id);
            throw new ResourceNotFoundException("MeasurementUnit with ID " + id + " not found");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted MeasurementUnit with ID: {}", id);
    }
}
