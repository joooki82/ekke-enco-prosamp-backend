package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.AdjustmentMethodMapper;
import hu.jakab.ekkeencoprosampbackend.model.AdjustmentMethod;
import hu.jakab.ekkeencoprosampbackend.repository.AdjustmentMethodRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdjustmentMethodService {

    private static final Logger logger = LoggerFactory.getLogger(AdjustmentMethodService.class);

    private final AdjustmentMethodRepository repository;
    private final AdjustmentMethodMapper mapper;

    @Autowired
    public AdjustmentMethodService(AdjustmentMethodRepository repository, AdjustmentMethodMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<AdjustmentMethodResponseDTO> getAll() {
        logger.info("Fetching all adjustment methods");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public AdjustmentMethodResponseDTO getById(Long id) {
        logger.info("Fetching adjustment method by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Adjustment method with ID " + id + " not found"));
    }

    @Transactional
    public AdjustmentMethodCreatedDTO save(AdjustmentMethodRequestDTO dto) {
        logger.info("Creating a new adjustment method with code: {}", dto.getCode());
        AdjustmentMethod adjustmentMethod = mapper.toEntity(dto);
        try {
            AdjustmentMethod savedMethod = repository.save(adjustmentMethod);
            return mapper.toCreatedDTO(savedMethod);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error saving adjustment method: Duplicate 'code' detected");
            throw new DuplicateResourceException("Failed to create adjustment method: Duplicate 'code' detected");
        }
    }

    @Transactional
    public AdjustmentMethodResponseDTO update(Long id, AdjustmentMethodRequestDTO dto) {
        logger.info("Updating adjustment method with ID: {}", id);
        AdjustmentMethod existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adjustment method with ID " + id + " not found"));

        if (dto.getCode() != null) existing.setCode(dto.getCode());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());

        try {
            AdjustmentMethod updatedMethod = repository.save(existing);
            return mapper.toResponseDTO(updatedMethod);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update adjustment method: Duplicate 'code' detected");
            throw new DuplicateResourceException("Update failed: Duplicate 'code' value");
        }
    }


    @Transactional
    public void delete(Long id) {
        logger.info("Deleting adjustment method with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Cannot delete: Adjustment method with ID {} not found", id);
            throw new ResourceNotFoundException("Adjustment method with ID " + id + " not found");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted adjustment method with ID: {}", id);
    }


}
