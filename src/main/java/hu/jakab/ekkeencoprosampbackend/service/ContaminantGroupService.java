package hu.jakab.ekkeencoprosampbackend.service;


import hu.jakab.ekkeencoprosampbackend.dto.contaminantGroup.ContaminantGroupCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.contaminantGroup.ContaminantGroupRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.contaminantGroup.ContaminantGroupResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.ContaminantGroupMapper;
import hu.jakab.ekkeencoprosampbackend.model.ContaminantGroup;
import hu.jakab.ekkeencoprosampbackend.repository.ContaminantGroupRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaminantGroupService {
    private static final Logger logger = LoggerFactory.getLogger(ContaminantGroupService.class);

    private final ContaminantGroupRepository repository;
    private final ContaminantGroupMapper mapper;

    @Autowired
    public ContaminantGroupService(ContaminantGroupRepository repository, ContaminantGroupMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ContaminantGroupResponseDTO> getAll() {
        logger.info("Fetching all ContaminantGroups");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public ContaminantGroupResponseDTO getById(Long id) {
        logger.info("Fetching ContaminantGroup by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("ContaminantGroup with ID " + id + " not found"));
    }

    @Transactional
    public ContaminantGroupCreatedDTO save(ContaminantGroupRequestDTO dto) {
        logger.info("Creating a new ContaminantGroup with ContaminantGroup name: {}", dto.getName());

        ContaminantGroup ContaminantGroup = mapper.toEntity(dto);

        try {
            ContaminantGroup savedContaminantGroup = repository.save(ContaminantGroup);
            return mapper.toCreatedDTO(savedContaminantGroup);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to save contaminant group with name '{}': Constraint violation - {}", dto.getName(), e.getMessage(), e);
            throw new DuplicateResourceException("A contaminant group with the name '" + dto.getName() + "' already exists.");
        }
    }

    @Transactional
    public ContaminantGroupResponseDTO update(Long id, ContaminantGroupRequestDTO dto) {
        logger.info("Updating ContaminantGroup (ID: {}) with new details", id);
        
        ContaminantGroup existing = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Update failed: Contaminant group with ID {} not found", id);
                    return new ResourceNotFoundException("Contaminant group with ID " + id + " not found.");
                });

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());

        try {
            ContaminantGroup updatedContaminantGroup = repository.save(existing);
            return mapper.toResponseDTO(updatedContaminantGroup);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update contaminant group with ID {} and name '{}': Constraint violation - {}", id, dto.getName(), e.getMessage(), e);
            throw new DuplicateResourceException("Update failed: A contaminant group with the name '" + dto.getName() + "' already exists.");
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting ContaminantGroup with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Delete failed: Contaminant group with ID {} not found", id);
            throw new ResourceNotFoundException("Contaminant group with ID " + id + " not found.");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted ContaminantGroup with ID: {}", id);
    }
}
