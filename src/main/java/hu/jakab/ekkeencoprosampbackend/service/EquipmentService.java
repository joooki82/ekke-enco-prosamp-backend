package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.equipment.EquipmentCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.equipment.EquipmentRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.equipment.EquipmentResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.EquipmentMapper;
import hu.jakab.ekkeencoprosampbackend.model.Equipment;
import hu.jakab.ekkeencoprosampbackend.repository.EquipmentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {
    private static final Logger logger = LoggerFactory.getLogger(EquipmentService.class);

    private final EquipmentRepository repository;
    private final EquipmentMapper mapper;

    @Autowired
    public EquipmentService(EquipmentRepository repository, EquipmentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<EquipmentResponseDTO> getAll() {
        logger.info("Fetching all Equipments");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public EquipmentResponseDTO getById(Long id) {
        logger.info("Fetching Equipment by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment with ID " + id + " not found"));
    }

    @Transactional
    public EquipmentCreatedDTO save(EquipmentRequestDTO dto) {
        logger.info("Creating a new Equipment with Equipment number: {}", dto.getName());

        Equipment Equipment = mapper.toEntity(dto);

        try {
            Equipment savedEquipment = repository.save(Equipment);
            return mapper.toCreatedDTO(savedEquipment);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error saving Equipment: Duplicate Equipment identifier detected");
            throw new DuplicateResourceException("Failed to create Equipment: Duplicate Equipment identifier detected");
        }
    }

    @Transactional
    public EquipmentResponseDTO update(Long id, EquipmentRequestDTO dto) {
        logger.info("Updating Equipment (ID: {}) with new details", id);
        
        Equipment existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment with ID " + id + " not found"));

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getIdentifier() != null) existing.setIdentifier(dto.getIdentifier());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getManufacturer() != null) existing.setManufacturer(dto.getManufacturer());
        if (dto.getType() != null) existing.setType(dto.getType());
        if (dto.getSerialNumber() != null) existing.setSerialNumber(dto.getSerialNumber());
        if (dto.getMeasuringRange() != null) existing.setMeasuringRange(dto.getMeasuringRange());
        if (dto.getResolution() != null) existing.setResolution(dto.getResolution());
        if (dto.getAccuracy() != null) existing.setAccuracy(dto.getAccuracy());
        if (dto.getCalibrationDate() != null) existing.setCalibrationDate(dto.getCalibrationDate());
        if (dto.getNextCalibrationDate() != null) existing.setNextCalibrationDate(dto.getNextCalibrationDate());


        try {
            Equipment updatedEquipment = repository.save(existing);
            logger.info("Successfully updated Equipment (ID: {})", id);
            return mapper.toResponseDTO(updatedEquipment);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update Equipment (ID: {}): Duplicate Identifier detected", id);
            throw new RuntimeException("Update failed: Duplicate identifier detected");
        } catch (Exception e) {
            logger.error("Unexpected error while updating Equipment (ID: {}): {}", id, e.getMessage());
            throw new RuntimeException("Update failed: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting Equipment with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Cannot delete: Equipment with ID {} not found", id);
            throw new ResourceNotFoundException("Equipment with ID " + id + " not found");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted Equipment with ID: {}", id);
    }
}
