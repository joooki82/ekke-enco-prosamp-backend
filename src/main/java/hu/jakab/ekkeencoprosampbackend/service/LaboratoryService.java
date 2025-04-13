package hu.jakab.ekkeencoprosampbackend.service;


import hu.jakab.ekkeencoprosampbackend.dto.laboratory.LaboratoryCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.laboratory.LaboratoryRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.laboratory.LaboratoryResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.LaboratoryMapper;
import hu.jakab.ekkeencoprosampbackend.model.Laboratory;
import hu.jakab.ekkeencoprosampbackend.repository.LaboratoryRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LaboratoryService {

    private static final Logger logger = LoggerFactory.getLogger(LaboratoryService.class);

    private final LaboratoryRepository repository;
    private final LaboratoryMapper mapper;

    @Autowired
    public LaboratoryService(LaboratoryRepository repository, LaboratoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<LaboratoryResponseDTO> getAll() {
        logger.info("Fetching all laboratorys");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public LaboratoryResponseDTO getById(Long id) {
        logger.info("Fetching laboratory by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("laboratory with ID " + id + " not found"));
    }

    @Transactional
    public LaboratoryCreatedDTO save(LaboratoryRequestDTO dto) {
        logger.info("Creating a new laboratory with code: {}", dto.getName());
        Laboratory Laboratory = mapper.toEntity(dto);
        try {
            Laboratory savedMethod = repository.save(Laboratory);
            return mapper.toCreatedDTO(savedMethod);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to save laboratory with name '{}': Constraint violation - {}", dto.getName(), e.getMessage(), e);
            throw new DuplicateResourceException("A laboratory with the name '" + dto.getName() + "' already exists.");
        }
    }

    @Transactional
    public LaboratoryResponseDTO update(Long id, LaboratoryRequestDTO dto) {
        logger.info("Updating laboratory with ID: {}", id);
        Laboratory existing = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Update failed: Laboratory with ID {} not found", id);
                    return new ResourceNotFoundException("Laboratory with ID " + id + " not found.");
                });

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getAccreditation() != null) existing.setAccreditation(dto.getAccreditation());
        if (dto.getContactEmail() != null) existing.setContactEmail(dto.getContactEmail());
        if (dto.getPhone() != null) existing.setPhone(dto.getPhone());
        if (dto.getAddress() != null) existing.setAddress(dto.getAddress());
        if (dto.getWebsite() != null) existing.setWebsite(dto.getWebsite());

        try {
            Laboratory updatedMethod = repository.save(existing);
            logger.info("Successfully updated laboratory with ID: {}", id);
            return mapper.toResponseDTO(updatedMethod);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update laboratory with ID {}, name '{}': Constraint violation - {}", id, dto.getName(), e.getMessage(), e);
            throw new DuplicateResourceException("Update failed: A laboratory with the name '" + dto.getName() + "' already exists.");
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting laboratory with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Cannot delete: laboratory with ID {} not found", id);
            throw new ResourceNotFoundException("laboratory with ID " + id + " not found");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted laboratory with ID: {}", id);
    }

}
