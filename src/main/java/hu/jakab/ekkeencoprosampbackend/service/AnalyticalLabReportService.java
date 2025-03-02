package hu.jakab.ekkeencoprosampbackend.service;


import hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport.AnalyticalLabReportCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport.AnalyticalLabReportRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport.AnalyticalLabReportResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.AnalyticalLabReportMapper;
import hu.jakab.ekkeencoprosampbackend.model.AnalyticalLabReport;
import hu.jakab.ekkeencoprosampbackend.model.Laboratory;
import hu.jakab.ekkeencoprosampbackend.repository.AnalyticalLabReportRepository;
import hu.jakab.ekkeencoprosampbackend.repository.LaboratoryRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticalLabReportService {

    private static final Logger logger = LoggerFactory.getLogger(AnalyticalLabReportService.class);

    private final AnalyticalLabReportRepository repository;
    private final LaboratoryRepository laboratoryRepository;
    private final AnalyticalLabReportMapper mapper;

    @Autowired
    public AnalyticalLabReportService(AnalyticalLabReportRepository repository, LaboratoryRepository laboratoryRepository, AnalyticalLabReportMapper mapper) {
        this.repository = repository;
        this.laboratoryRepository = laboratoryRepository;
        this.mapper = mapper;
    }

    public List<AnalyticalLabReportResponseDTO> getAll() {
        logger.info("Fetching all AnalyticalLabReports");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public AnalyticalLabReportResponseDTO getById(Long id) {
        logger.info("Fetching AnalyticalLabReport by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("AnalyticalLabReport with ID " + id + " not found"));
    }

    @Transactional
    public AnalyticalLabReportCreatedDTO save(AnalyticalLabReportRequestDTO dto) {
        logger.info("Creating a new AnalyticalLabReport with report number: {}", dto.getReportNumber());
        AnalyticalLabReport AnalyticalLabReport = mapper.toEntity(dto);
        try {
            AnalyticalLabReport savedAnalyticalLabReport = repository.save(AnalyticalLabReport);
            return mapper.toCreatedDTO(savedAnalyticalLabReport);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error saving AnalyticalLabReport: Duplicate report number");
            throw new DuplicateResourceException("Failed to create AnalyticalLabReport: Duplicate report number");
        }
    }

    @Transactional
    public AnalyticalLabReportResponseDTO update(Long id, AnalyticalLabReportRequestDTO dto) {
        logger.info("Updating AnalyticalLabReport (ID: {}) with new details", id);
        AnalyticalLabReport existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AnalyticalLabReport with ID " + id + " not found"));

        Laboratory existingLaboratory = laboratoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AnalyticalLabReport with ID " + id + " not found"));

        if (dto.getReportNumber() != null) existing.setReportNumber(dto.getReportNumber());
        if (dto.getIssueDate() != null) existing.setLaboratory(existingLaboratory);
        if (dto.getIssueDate() != null) existing.setIssueDate(dto.getIssueDate());

        try {
            AnalyticalLabReport updatedAnalyticalLabReport = repository.save(existing);
            return mapper.toResponseDTO(updatedAnalyticalLabReport);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update AnalyticalLabReport: Duplicate report number detected");
            throw new DuplicateResourceException("Update failed: Duplicate report number");
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting AnalyticalLabReport with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Cannot delete: AnalyticalLabReport with ID {} not found", id);
            throw new ResourceNotFoundException("AnalyticalLabReport with ID " + id + " not found");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted AnalyticalLabReport with ID: {}", id);
    }
}
