package hu.jakab.ekkeencoprosampbackend.service;


import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.ContaminantMapper;
import hu.jakab.ekkeencoprosampbackend.model.Contaminant;
import hu.jakab.ekkeencoprosampbackend.repository.ContaminantGroupRepository;
import hu.jakab.ekkeencoprosampbackend.repository.ContaminantRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaminantService {
    private static final Logger logger = LoggerFactory.getLogger(ContaminantService.class);

    private final ContaminantRepository repository;
    private final ContaminantGroupRepository contaminantGroupRepository;
    private final ContaminantMapper mapper;

    @Autowired
    public ContaminantService(ContaminantRepository repository, ContaminantGroupRepository contaminantGroupRepository, ContaminantMapper mapper) {
        this.repository = repository;
        this.contaminantGroupRepository = contaminantGroupRepository;
        this.mapper = mapper;
    }

    public List<ContaminantResponseDTO> getAll() {
        logger.info("Fetching all Contaminants");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public ContaminantResponseDTO getById(Long id) {
        logger.info("Fetching Contaminant by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Contaminant with ID " + id + " not found"));
    }

    @Transactional
    public ContaminantCreatedDTO save(ContaminantRequestDTO dto) {
        logger.info("Creating a new Contaminant with Contaminant name: {}", dto.getName());

        Contaminant contaminant = mapper.toEntity(dto);

        contaminant.setContaminantGroup(
                contaminantGroupRepository.findById(dto.getContaminantGroupId())
                        .orElseThrow(() -> {
                            logger.warn("Save failed: ContaminantGroup with ID {} not found", dto.getContaminantGroupId());
                            return new ResourceNotFoundException("Contaminant group with ID " + dto.getContaminantGroupId() + " not found.");
                        })
        );

        try {
            Contaminant savedContaminant = repository.save(contaminant);
            return mapper.toCreatedDTO(savedContaminant);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to save contaminant with name '{}': Constraint violation - {}", dto.getName(), e.getMessage(), e);
            throw new DuplicateResourceException("A contaminant with the name '" + dto.getName() + "' already exists.");
        }
    }

    @Transactional
    public ContaminantResponseDTO update(Long id, ContaminantRequestDTO dto) {
        logger.info("Updating Contaminant (ID: {}) with new details", id);

        Contaminant existing = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Update failed: Contaminant with ID {} not found", id);
                    return new ResourceNotFoundException("Contaminant with ID " + id + " not found.");
                });


        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getContaminantGroupId() != null) {
            existing.setContaminantGroup(
                    contaminantGroupRepository.findById(dto.getContaminantGroupId())
                            .orElseThrow(() -> {
                                logger.warn("Update failed: ContaminantGroup with ID {} not found", dto.getContaminantGroupId());
                                return new ResourceNotFoundException("Contaminant group with ID " + dto.getContaminantGroupId() + " not found.");
                            })
            );
        }

        try {
            Contaminant updatedContaminant = repository.save(existing);
            return mapper.toResponseDTO(updatedContaminant);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update contaminant with ID {} and name '{}': Constraint violation - {}", id, dto.getName(), e.getMessage(), e);
            throw new DuplicateResourceException("Update failed: A contaminant with the name '" + dto.getName() + "' already exists.");
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting Contaminant with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Delete failed: Contaminant with ID {} not found", id);
            throw new ResourceNotFoundException("Contaminant with ID " + id + " not found.");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted Contaminant with ID: {}", id);
    }
}
