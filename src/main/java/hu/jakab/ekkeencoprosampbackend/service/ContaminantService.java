package hu.jakab.ekkeencoprosampbackend.service;


import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.ContaminantMapper;
import hu.jakab.ekkeencoprosampbackend.model.Contaminant;
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
    private final ContaminantMapper mapper;

    @Autowired
    public ContaminantService(ContaminantRepository repository, ContaminantMapper mapper) {
        this.repository = repository;
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

        Contaminant Contaminant = mapper.toEntity(dto);

        try {
            Contaminant savedContaminant = repository.save(Contaminant);
            return mapper.toCreatedDTO(savedContaminant);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error saving Contaminant: Duplicate Contaminant name detected");
            throw new DuplicateResourceException("Failed to create Contaminant: Duplicate Contaminant name detected");
        }
    }

    @Transactional
    public ContaminantResponseDTO update(Long id, ContaminantRequestDTO dto) {
        logger.info("Updating Contaminant (ID: {}) with new details", id);

        Contaminant existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contaminant with ID " + id + " not found"));


        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());

        try {
            Contaminant updatedContaminant = repository.save(existing);
            return mapper.toResponseDTO(updatedContaminant);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update Contaminant: Duplicate Contaminant name detected");
            throw new RuntimeException("Update failed: Duplicate Contaminant name detected");
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting Contaminant with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Cannot delete: Contaminant with ID {} not found", id);
            throw new ResourceNotFoundException("Contaminant with ID " + id + " not found");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted Contaminant with ID: {}", id);
    }
}
