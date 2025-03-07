package hu.jakab.ekkeencoprosampbackend.service;


import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.TestReportMapper;
import hu.jakab.ekkeencoprosampbackend.model.TestReport;
import hu.jakab.ekkeencoprosampbackend.model.TestReportStatus;
import hu.jakab.ekkeencoprosampbackend.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestReportService {
    private static final Logger logger = LoggerFactory.getLogger(TestReportService.class);

    private final TestReportMapper mapper;
    private final TestReportRepository repository;
    private final ProjectRepository projectRepository;
    private final LocationRepository locationRepository;
    private final SamplingRecordDatM200Repository samplingRecordRepository;
    private final TestReportStandardRepository testReportStandardRepository;
    private final TestReportSamplerRepository testReportSamplerRepository;


    @Autowired
    public TestReportService(TestReportRepository repository, TestReportMapper mapper, ProjectRepository projectRepository, LocationRepository locationRepository, SamplingRecordDatM200Repository samplingRecordRepository, TestReportStandardRepository testReportStandardRepository, TestReportSamplerRepository testReportSamplerRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.projectRepository = projectRepository;
        this.locationRepository = locationRepository;
        this.samplingRecordRepository = samplingRecordRepository;
        this.testReportStandardRepository = testReportStandardRepository;
        this.testReportSamplerRepository = testReportSamplerRepository;
    }

    public List<TestReportResponseDTO> getAll() {
        logger.info("Fetching all TestReports");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public TestReportResponseDTO getById(Long id) {
        logger.info("Fetching TestReport by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("TestReport with ID " + id + " not found"));
    }

    @Transactional
    public TestReportCreatedDTO save(TestReportRequestDTO dto) {
        logger.info("Creating a new TestReport with TestReport TestReportNumber: {}", dto.getReportNumber());

        TestReport TestReport = mapper.toEntity(dto);

        try {
            TestReport savedTestReport = repository.save(TestReport);
            return mapper.toCreatedDTO(savedTestReport);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error saving TestReport: Duplicate TestReport TestReportNumber detected");
            throw new DuplicateResourceException("Failed to create TestReport: Duplicate TestReport TestReportNumber detected");
        }
    }

    @Transactional
    public TestReportResponseDTO update(Long id, TestReportRequestDTO dto) {
        logger.info("Updating TestReport (ID: {}) with new details", id);

        TestReport existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TestReport with ID " + id + " not found"));

        // Update report number only if it's different and unique
        if (dto.getReportNumber() != null && !dto.getReportNumber().equals(existing.getReportNumber())) {
            if (repository.existsByReportNumber(dto.getReportNumber())) {
                throw new DataIntegrityViolationException("TestReport with report number " + dto.getReportNumber() + " already exists");
            }
            existing.setReportNumber(dto.getReportNumber());
        }

        // Update fields if new values are provided
        if (dto.getTitle() != null) {
            existing.setTitle(dto.getTitle());
        }
        if (dto.getApprovedBy() != null) {
            existing.setApprovedBy(dto.getApprovedBy());
        }
        if (dto.getPreparedBy() != null) {
            existing.setPreparedBy(dto.getPreparedBy());
        }
        if (dto.getCheckedBy() != null) {
            existing.setCheckedBy(dto.getCheckedBy());
        }
        if (dto.getAimOfTest() != null) {
            existing.setAimOfTest(dto.getAimOfTest());
        }
        if (dto.getTechnology() != null) {
            existing.setTechnology(dto.getTechnology());
        }
        if (dto.getSamplingConditionsDates() != null) {
            existing.setSamplingConditionsDates(dto.getSamplingConditionsDates());
        }
        if (dto.getDeterminationOfPollutantConcentration() != null) {
            existing.setDeterminationOfPollutantConcentration(dto.getDeterminationOfPollutantConcentration());
        }
        if (dto.getIssueDate() != null) {
            existing.setIssueDate(dto.getIssueDate());
        }
        if (dto.getReportStatus() != null) {
            existing.setReportStatus(TestReportStatus.valueOf(dto.getReportStatus()));
        }

        // Update project, location, and sampling record if provided
        if (dto.getProjectId() != null) {
            existing.setProject(projectRepository.findById(dto.getProjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Project with ID " + dto.getProjectId() + " not found")));
        }
        if (dto.getLocationId() != null) {
            existing.setLocation(locationRepository.findById(dto.getLocationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Location with ID " + dto.getLocationId() + " not found")));
        }
        if (dto.getSamplingRecordId() != null) {
            existing.setSamplingRecord(samplingRecordRepository.findById(dto.getSamplingRecordId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sampling record with ID " + dto.getSamplingRecordId() + " not found")));
        }

        // Update related standards if provided
        if (dto.getTestReportStandardIds() != null) {
            existing.getTestReportStandards().clear();
            existing.getTestReportStandards().addAll(
                    testReportStandardRepository.findAllById(dto.getTestReportStandardIds()));
        }

        // Update related samplers if provided
        if (dto.getTestReportSamplerIds() != null) {
            existing.getTestReportSamplers().clear();
            existing.getTestReportSamplers().addAll(
                    testReportSamplerRepository.findAllById(dto.getTestReportSamplerIds()));
        }

        try {
            TestReport updatedTestReport = repository.save(existing);
            return mapper.toResponseDTO(updatedTestReport);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update TestReport (ID: {}): {}", id, e.getMessage());
            throw new RuntimeException("Update failed: Duplicate TestReport report number detected");
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting TestReport with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Cannot delete: TestReport with ID {} not found", id);
            throw new ResourceNotFoundException("TestReport with ID " + id + " not found");
        }
        repository.deleteById(id);
        logger.info("Successfully deleted TestReport with ID: {}", id);
    }
}
