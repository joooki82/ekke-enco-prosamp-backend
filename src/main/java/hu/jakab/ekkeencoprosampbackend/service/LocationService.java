package hu.jakab.ekkeencoprosampbackend.service;


import hu.jakab.ekkeencoprosampbackend.dto.location.LocationCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.location.LocationRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.location.LocationResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.LocationMapper;
import hu.jakab.ekkeencoprosampbackend.model.Company;
import hu.jakab.ekkeencoprosampbackend.model.Location;
import hu.jakab.ekkeencoprosampbackend.repository.CompanyRepository;
import hu.jakab.ekkeencoprosampbackend.repository.LocationRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

    private final LocationRepository repository;
    private final CompanyRepository companyRepository;

    private final LocationMapper mapper;

    @Autowired
    public LocationService(LocationRepository repository, CompanyRepository companyRepository, LocationMapper mapper) {
        this.repository = repository;
        this.companyRepository = companyRepository;
        this.mapper = mapper;
    }

    public List<LocationResponseDTO> getAll() {
        logger.info("Fetching all locations");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public LocationResponseDTO getById(Long id) {
        logger.info("Fetching location by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Location with ID " + id + " not found"));
    }

    @Transactional
    public LocationCreatedDTO save(LocationRequestDTO dto) {
        logger.info("Creating a new location with name: {}", dto.getName());
        Location Location = mapper.toEntity(dto);
        try {
            Location savedLocation = repository.save(Location);
            return mapper.toCreatedDTO(savedLocation);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error saving location: Duplicate name ");
            throw new DuplicateResourceException("Failed to create location: Duplicate name ");
        }
    }

    @Transactional
    public LocationResponseDTO update(Long id, LocationRequestDTO dto) {
        logger.info("Updating location (ID: {}) with new details", id);
        Location existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location with ID " + id + " not found"));

        Company existingCompany = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company with ID " + dto.getCompanyId() + " not found"));

        if (dto.getCompanyId() != null) existing.setCompany(existingCompany);
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getAddress() != null) existing.setAddress(dto.getAddress());
        if (dto.getContactPerson() != null) existing.setContactPerson(dto.getContactPerson());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
        if (dto.getPhone() != null) existing.setPhone(dto.getPhone());
        if (dto.getCountry() != null) existing.setCountry(dto.getCountry());
        if (dto.getCity() != null) existing.setCity(dto.getCity());
        if (dto.getPostalCode() != null) existing.setPostalCode(dto.getPostalCode());

        try {
            Location updatedLocation = repository.save(existing);
            return mapper.toResponseDTO(updatedLocation);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update location: Duplicate name detected");
            throw new DuplicateResourceException("Update failed: Duplicate name ");
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting location with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Cannot delete: location with ID {} not found", id);
            throw new ResourceNotFoundException("Location with ID " + id + " not found");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted location with ID: {}", id);
    }
}
