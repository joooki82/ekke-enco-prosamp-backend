package hu.jakab.ekkeencoprosampbackend.service;


import hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200.SamplingRecordDatM200CreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200.SamplingRecordDatM200RequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200.SamplingRecordDatM200ResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.SamplingRecordDatM200Mapper;
import hu.jakab.ekkeencoprosampbackend.model.*;
import hu.jakab.ekkeencoprosampbackend.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SamplingRecordDatM200Service {

    private static final Logger logger = LoggerFactory.getLogger(SamplingRecordDatM200Service.class);

    private final SamplingRecordDatM200Repository repository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final CompanyRepository companyRepository;
    private final ProjectRepository projectRepository;
    private final SamplingRecordEquipmentRepository samplingRecordEquipmentRepository;
    private final EquipmentRepository equipmentRepository;

    private final SamplingRecordDatM200Mapper mapper;

    @Autowired
    public SamplingRecordDatM200Service(SamplingRecordDatM200Repository repository,
                                        UserRepository userRepository,
                                        LocationRepository locationRepository,
                                        CompanyRepository companyRepository,
                                        ProjectRepository projectRepository,
                                        SamplingRecordEquipmentRepository samplingRecordEquipmentRepository,
                                        EquipmentRepository equipmentRepository,
                                        SamplingRecordDatM200Mapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.companyRepository = companyRepository;
        this.projectRepository = projectRepository;
        this.samplingRecordEquipmentRepository = samplingRecordEquipmentRepository;
        this.equipmentRepository = equipmentRepository;
        this.mapper = mapper;
    }

    public List<SamplingRecordDatM200ResponseDTO> getAll() {
        logger.info("Fetching all SamplingRecordDatM200s");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public SamplingRecordDatM200ResponseDTO getById(Long id) {
        logger.info("Fetching SamplingRecordDatM200 by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("SamplingRecordDatM200 with ID " + id + " not found"));
    }

    @Transactional
    public SamplingRecordDatM200CreatedDTO save(SamplingRecordDatM200RequestDTO dto) {
        logger.info("Creating a new SamplingRecordDatM200 with sampling date: {}", dto.getSamplingDate());
        logger.info("Creating a new SamplingRecordDatM200 with tested plant: {}", dto.getTestedPlant());
        SamplingRecordDatM200 samplingRecordDatM200 = mapper.toEntity(dto);

        // Fetch equipment entities and create SamplingRecordEquipment entries
        List<SamplingRecordEquipment> equipmentLinks = dto.getEquipmentIds().stream()
                .map(equipmentId -> {
                    Equipment equipment = equipmentRepository.findById(equipmentId)
                            .orElseThrow(() -> new RuntimeException("Equipment not found: " + equipmentId));
                    return SamplingRecordEquipment.builder()
                            .samplingRecord(samplingRecordDatM200)
                            .equipment(equipment)
                            .build();
                })
                .collect(Collectors.toList());

        samplingRecordDatM200.setSamplingRecordEquipments(equipmentLinks);

        try {
            SamplingRecordDatM200 savedSamplingRecordDatM200 = repository.save(samplingRecordDatM200);
            return mapper.toCreatedDTO(savedSamplingRecordDatM200);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error saving SamplingRecordDatM200: Duplicate field ");
            throw new DuplicateResourceException("Failed to create SamplingRecordDatM200: Duplicate field ");
        }
    }

    @Transactional
    public SamplingRecordDatM200ResponseDTO update(Long id, SamplingRecordDatM200RequestDTO dto) {
        logger.info("Updating SamplingRecordDatM200 (ID: {}) with new details", id);
        // Fetch the existing record
        SamplingRecordDatM200 existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SamplingRecordDatM200 with ID " + id + " not found"));

        try {
            // Apply updates from DTO
            existing.setSamplingDate(dto.getSamplingDate());
            existing.setExposureTime(dto.getExposureTime());
            existing.setTestedPlant(dto.getTestedPlant());
            existing.setTechnology(dto.getTechnology());
            existing.setShiftCountAndDuration(dto.getShiftCountAndDuration());
            existing.setWorkersPerShift(dto.getWorkersPerShift());
            existing.setTemperature(dto.getTemperature());
            existing.setHumidity(dto.getHumidity());
            existing.setWindSpeed(dto.getWindSpeed());
            existing.setPressure1(dto.getPressure1());
            existing.setPressure2(dto.getPressure2());
            existing.setOtherEnvironmentalConditions(dto.getOtherEnvironmentalConditions());
            existing.setAirFlowConditions(dto.getAirFlowConditions());
            existing.setOperationMode(dto.getOperationMode());
            existing.setOperationBreak(dto.getOperationBreak());
            existing.setLocalAirExtraction(dto.getLocalAirExtraction());
            existing.setSerialNumbersOfSamples(dto.getSerialNumbersOfSamples());
            existing.setRemarks(dto.getRemarks());

            // Update related entities using IDs
            if (dto.getConductedById() != null) {
                User conductedBy = userRepository.findById(dto.getConductedById())
                        .orElseThrow(() -> new ResourceNotFoundException("User with ID " + dto.getConductedById() + " not found"));
                existing.setConductedBy(conductedBy);
            }

            if (dto.getSiteLocationId() != null) {
                Location siteLocation = locationRepository.findById(dto.getSiteLocationId())
                        .orElseThrow(() -> new ResourceNotFoundException("Location with ID " + dto.getSiteLocationId() + " not found"));
                existing.setSiteLocation(siteLocation);
            }

            if (dto.getCompanyId() != null) {
                Company company = companyRepository.findById(dto.getCompanyId())
                        .orElseThrow(() -> new ResourceNotFoundException("Company with ID " + dto.getCompanyId() + " not found"));
                existing.setCompany(company);
            }

            if (dto.getProjectId() != null) {
                Project project = projectRepository.findById(dto.getProjectId())
                        .orElseThrow(() -> new ResourceNotFoundException("Project with ID " + dto.getProjectId() + " not found"));
                existing.setProject(project);
            }

            if (dto.getStatus() != null) {
                existing.setStatus(SamplingRecordStatus.valueOf(dto.getStatus()));
            }

            // ✅ Fix: Handle Equipment List Update Correctly
            if (dto.getEquipmentIds() != null) {
                List<Equipment> selectedEquipments = equipmentRepository.findAllById(dto.getEquipmentIds());

                // Remove old mappings that are no longer in the new list
                existing.getSamplingRecordEquipments().removeIf(equipmentMapping ->
                        !dto.getEquipmentIds().contains(equipmentMapping.getEquipment().getId()));

                // Add new mappings
                for (Equipment equipment : selectedEquipments) {
                    if (existing.getSamplingRecordEquipments().stream()
                            .noneMatch(mapping -> mapping.getEquipment().getId().equals(equipment.getId()))) {
                        SamplingRecordEquipment newEquipmentMapping = new SamplingRecordEquipment();
                        newEquipmentMapping.setSamplingRecord(existing);
                        newEquipmentMapping.setEquipment(equipment);
                        existing.getSamplingRecordEquipments().add(newEquipmentMapping);
                    }
                }
            }

            // Save updated entity
            SamplingRecordDatM200 updatedSamplingRecordDatM200 = repository.save(existing);

            // Convert to response DTO
            return mapper.toResponseDTO(updatedSamplingRecordDatM200);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update SamplingRecordDatM200 (ID: {}): Duplicate field detected", id);
            throw new DuplicateResourceException("Update failed: Duplicate field detected");
        } catch (Exception e) {
            logger.error("Unexpected error while updating SamplingRecordDatM200 (ID: {}): {}", id, e.getMessage());
            throw new RuntimeException("Update failed: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting SamplingRecordDatM200 with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Cannot delete: SamplingRecordDatM200 with ID {} not found", id);
            throw new ResourceNotFoundException("SamplingRecordDatM200 with ID " + id + " not found");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted SamplingRecordDatM200 with ID: {}", id);
    }
}
