package hu.jakab.ekkeencoprosampbackend.service;


import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;

import hu.jakab.ekkeencoprosampbackend.mapper.CompanyMapper;
import hu.jakab.ekkeencoprosampbackend.model.Company;
import hu.jakab.ekkeencoprosampbackend.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyRepository repository;
    private final CompanyMapper mapper;

    @Autowired
    public CompanyService(CompanyRepository repository, CompanyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CompanyResponseDTO> getAll() {
        logger.info("Fetching all companies");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public CompanyResponseDTO getById(Long id) {
        logger.info("Fetching company by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("company with ID " + id + " not found"));
    }

    @Transactional
    public CompanyCreatedDTO save(CompanyRequestDTO dto) {
        logger.info("Creating a new company with name: {}", dto.getName());
        Company Company = mapper.toEntity(dto);
        try {
            Company savedCompany = repository.save(Company);
            return mapper.toCreatedDTO(savedCompany);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error saving company: Duplicate name or tax number");
            throw new DuplicateResourceException("Failed to create Company: Duplicate name or tax number");
        }
    }

    @Transactional
    public CompanyResponseDTO update(Long id, CompanyRequestDTO dto) {
        logger.info("Updating company (ID: {}) with new details", id);
        Company existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("company with ID " + id + " not found"));

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getAddress() != null) existing.setAddress(dto.getAddress());
        if (dto.getContactPerson() != null) existing.setContactPerson(dto.getContactPerson());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
        if (dto.getPhone() != null) existing.setPhone(dto.getPhone());
        if (dto.getCountry() != null) existing.setCountry(dto.getCountry());
        if (dto.getCity() != null) existing.setCity(dto.getCity());


        try {
            Company updatedCompany = repository.save(existing);
            return mapper.toResponseDTO(updatedCompany);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update Company: Duplicate name or tax number detected");
            throw new DuplicateResourceException("Update failed: Duplicate name or tax number");
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting company with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Cannot delete: company with ID {} not found", id);
            throw new ResourceNotFoundException("company with ID " + id + " not found");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted company with ID: {}", id);
    }
}
