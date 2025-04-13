package hu.jakab.ekkeencoprosampbackend.service;


import hu.jakab.ekkeencoprosampbackend.dto.standard.StandardCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.standard.StandardRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.standard.StandardResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.StandardMapper;
import hu.jakab.ekkeencoprosampbackend.model.Standard;
import hu.jakab.ekkeencoprosampbackend.repository.StandardRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandardService {
    private static final Logger logger = LoggerFactory.getLogger(StandardService.class);

    private final StandardMapper mapper;
    private final StandardRepository repository;

    @Autowired
    public StandardService(StandardRepository repository, StandardMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<StandardResponseDTO> getAll() {
        logger.info("Fetching all Standards");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public StandardResponseDTO getById(Long id) {
        logger.info("Fetching Standard by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Standard with ID " + id + " not found"));
    }

    @Transactional
    public StandardCreatedDTO save(StandardRequestDTO dto) {
        logger.info("Creating new Standard with standardNumber: '{}', identifier: '{}'", dto.getStandardNumber(), dto.getIdentifier());

        Standard standard = mapper.toEntity(dto);

        try {
            Standard savedStandard = repository.save(standard);
            return mapper.toCreatedDTO(savedStandard);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to save Standard with standardNumber '{}': Constraint violation - {}", dto.getStandardNumber(), e.getMessage(), e);
            throw new DuplicateResourceException("A standard with number '" + dto.getStandardNumber() + "' or identifier '" + dto.getIdentifier() + "' already exists.");
        }
    }

    @Transactional
    public StandardResponseDTO update(Long id, StandardRequestDTO dto) {
        logger.info("Updating Standard with ID: {}", id);

        Standard existing = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Update failed: Standard with ID {} not found", id);
                    return new ResourceNotFoundException("Standard with ID " + id + " not found.");
                });

        if (dto.getIdentifier() != null && !dto.getIdentifier().equals(existing.getIdentifier())) {
            if (repository.existsByIdentifier(dto.getIdentifier())) {
                logger.warn("Update conflict: Standard with identifier '{}' already exists", dto.getIdentifier());
                throw new DuplicateResourceException("A standard with identifier '" + dto.getIdentifier() + "' already exists.");
            }
            existing.setIdentifier(dto.getIdentifier());
        }

        if (dto.getStandardNumber() != null) {
            existing.setStandardNumber(dto.getStandardNumber());
        }
        if (dto.getDescription() != null) {
            existing.setDescription(dto.getDescription());
        }
        if (dto.getStandardType() != null) {
            existing.setStandardType(dto.getStandardType());
        }

        try {
            Standard updatedStandard = repository.save(existing);
            logger.info("Successfully updated Standard with ID: {}", id);
            return mapper.toResponseDTO(updatedStandard);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update Standard with ID {}, identifier '{}': Constraint violation - {}", id, dto.getIdentifier(), e.getMessage(), e);
            throw new DuplicateResourceException("Update failed: A standard with identifier '" + dto.getIdentifier() + "' already exists.");
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting Standard with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Cannot delete: Standard with ID {} not found", id);
            throw new ResourceNotFoundException("Standard with ID " + id + " not found");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted Standard with ID: {}", id);
    }
}
