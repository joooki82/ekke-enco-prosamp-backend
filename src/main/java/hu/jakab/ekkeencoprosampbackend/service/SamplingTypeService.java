package hu.jakab.ekkeencoprosampbackend.service;


import hu.jakab.ekkeencoprosampbackend.dto.samplingType.SamplingTypeCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingType.SamplingTypeRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingType.SamplingTypeResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.SamplingTypeMapper;
import hu.jakab.ekkeencoprosampbackend.model.SamplingType;
import hu.jakab.ekkeencoprosampbackend.repository.SamplingTypeRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SamplingTypeService {
    private static final Logger logger = LoggerFactory.getLogger(SamplingTypeService.class);

    private final SamplingTypeMapper mapper;
    private final SamplingTypeRepository repository;

    @Autowired
    public SamplingTypeService(SamplingTypeRepository repository, SamplingTypeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<SamplingTypeResponseDTO> getAll() {
        logger.info("Fetching all SamplingTypes");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public SamplingTypeResponseDTO getById(Long id) {
        logger.info("Fetching SamplingType by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("SamplingType with ID " + id + " not found"));
    }

    @Transactional
    public SamplingTypeCreatedDTO save(SamplingTypeRequestDTO dto) {
        logger.info("Creating a new SamplingType with code: '{}'", dto.getCode());

        SamplingType SamplingType = mapper.toEntity(dto);

        try {
            SamplingType savedSamplingType = repository.save(SamplingType);
            return mapper.toCreatedDTO(savedSamplingType);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to save SamplingType with code '{}': Constraint violation - {}", dto.getCode(), e.getMessage(), e);
            throw new DuplicateResourceException("A sampling type with code '" + dto.getCode() + "' already exists.");
        }
    }

    @Transactional
    public SamplingTypeResponseDTO update(Long id, SamplingTypeRequestDTO dto) {
        logger.info("Updating SamplingType (ID: {}) with new details", id);

        SamplingType existing = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Update failed: SamplingType with ID {} not found", id);
                    return new ResourceNotFoundException("SamplingType with ID " + id + " not found.");
                });

        if (dto.getCode() != null && !dto.getCode().equals(existing.getCode())) {
            if (repository.existsByCode(dto.getCode())) {
                logger.warn("Update conflict: SamplingType with code '{}' already exists", dto.getCode());
                throw new DuplicateResourceException("A sampling type with code '" + dto.getCode() + "' already exists.");
            }
            existing.setCode(dto.getCode());
        }

        if (dto.getDescription() != null) {
            existing.setDescription(dto.getDescription());
        }

        try {
            SamplingType updatedSamplingType = repository.save(existing);
            logger.info("Successfully updated SamplingType with ID: {}", id);
            return mapper.toResponseDTO(updatedSamplingType);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update SamplingType with ID {} and code '{}': Constraint violation - {}", id, dto.getCode(), e.getMessage(), e);
            throw new DuplicateResourceException("Update failed: A sampling type with code '" + dto.getCode() + "' already exists.");
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting SamplingType with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Cannot delete: SamplingType with ID {} not found", id);
            throw new ResourceNotFoundException("SamplingType with ID " + id + " not found");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted SamplingType with ID: {}", id);
    }
}
