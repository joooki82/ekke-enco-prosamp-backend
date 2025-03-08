package hu.jakab.ekkeencoprosampbackend.service;


import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.TestReportMapper;
import hu.jakab.ekkeencoprosampbackend.model.*;
import hu.jakab.ekkeencoprosampbackend.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private final UserRepository userRepository;
    private final StandardRepository standardRepository;


    @Autowired
    public TestReportService(TestReportRepository repository, TestReportMapper mapper, ProjectRepository projectRepository, LocationRepository locationRepository, SamplingRecordDatM200Repository samplingRecordRepository, TestReportStandardRepository testReportStandardRepository, TestReportSamplerRepository testReportSamplerRepository, UserRepository userRepository, StandardRepository standardRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.projectRepository = projectRepository;
        this.locationRepository = locationRepository;
        this.samplingRecordRepository = samplingRecordRepository;
        this.testReportStandardRepository = testReportStandardRepository;
        this.testReportSamplerRepository = testReportSamplerRepository;
        this.userRepository = userRepository;
        this.standardRepository = standardRepository;
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

        // Check for duplicate report number
        if (repository.existsByReportNumber(dto.getReportNumber())) {
            throw new DuplicateResourceException("Failed to create TestReport: Duplicate report number detected");
        }

        // Fetch required entities in batch
        Map<Long, Project> projectMap = projectRepository.findAllById(Collections.singletonList(dto.getProjectId()))
                .stream().collect(Collectors.toMap(Project::getId, Function.identity()));

        Map<Long, Location> locationMap = locationRepository.findAllById(Collections.singletonList(dto.getLocationId()))
                .stream().collect(Collectors.toMap(Location::getId, Function.identity()));

        Map<Long, SamplingRecordDatM200> samplingRecordMap = samplingRecordRepository.findAllById(Collections.singletonList(dto.getSamplingRecordId()))
                .stream().collect(Collectors.toMap(SamplingRecordDatM200::getId, Function.identity()));

        // Validate fetched entities
        Project project = Optional.ofNullable(projectMap.get(dto.getProjectId()))
                .orElseThrow(() -> new ResourceNotFoundException("Project with ID " + dto.getProjectId() + " not found"));

        Location location = Optional.ofNullable(locationMap.get(dto.getLocationId()))
                .orElseThrow(() -> new ResourceNotFoundException("Location with ID " + dto.getLocationId() + " not found"));

        SamplingRecordDatM200 samplingRecord = Optional.ofNullable(samplingRecordMap.get(dto.getSamplingRecordId()))
                .orElseThrow(() -> new ResourceNotFoundException("Sampling Record with ID " + dto.getSamplingRecordId() + " not found"));

        // Use mapper to convert DTO to Entity
        TestReport testReport = mapper.toEntity(dto);
        testReport.setProject(project);
        testReport.setLocation(location);
        testReport.setSamplingRecord(samplingRecord);
        testReport.setReportStatus(TestReportStatus.valueOf(dto.getReportStatus()));

        try {
            // Save test report first
            TestReport savedTestReport = repository.save(testReport);

            // Fetch standards in bulk
            List<Standard> standards = standardRepository.findAllById(dto.getTestReportStandardIds());

            // Convert to TestReportStandard entities
            List<TestReportStandard> testReportStandards = standards.stream()
                    .map(standard -> TestReportStandard.builder()
                            .testReport(savedTestReport)
                            .standard(standard)
                            .build())
                    .collect(Collectors.toList());

            testReportStandardRepository.saveAll(testReportStandards);

            // Fetch users in bulk for samplers
            if (dto.getTestReportSamplerIds() != null && !dto.getTestReportSamplerIds().isEmpty()) {
                List<User> samplers = userRepository.findAllById(dto.getTestReportSamplerIds());

                List<TestReportSampler> testReportSamplers = samplers.stream()
                        .map(user -> TestReportSampler.builder()
                                .testReport(savedTestReport)
                                .user(user)
                                .build())
                        .collect(Collectors.toList());

                testReportSamplerRepository.saveAll(testReportSamplers);
            }

            // Convert entity to DTO using mapper
            return mapper.toCreatedDTO(savedTestReport);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error saving TestReport: {}", e.getMessage());
            throw new DuplicateResourceException("Failed to create TestReport: Duplicate report number detected");
        }
    }

    @Transactional
    public TestReportResponseDTO update(Long id, TestReportRequestDTO dto) {
        logger.info("Updating TestReport (ID: {}) with new details", id);

        TestReport existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TestReport with ID " + id + " not found"));

        // Check for duplicate report number if changed
        if (dto.getReportNumber() != null && !dto.getReportNumber().equals(existing.getReportNumber())) {
            if (repository.existsByReportNumber(dto.getReportNumber())) {
                throw new DataIntegrityViolationException("TestReport with report number " + dto.getReportNumber() + " already exists");
            }
            existing.setReportNumber(dto.getReportNumber());
        }

        // Use mapper to update non-null fields in existing entity
        mapper.updateEntityFromDTO(dto, existing);

        // Fetch and update project, location, and sampling record if provided
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
            List<Standard> standards = standardRepository.findAllById(dto.getTestReportStandardIds());
            List<TestReportStandard> testReportStandards = standards.stream()
                    .map(standard -> TestReportStandard.builder()
                            .testReport(existing)
                            .standard(standard)
                            .build())
                    .toList();

            existing.getTestReportStandards().clear();
            existing.getTestReportStandards().addAll(testReportStandards);
        }

        // Update related samplers if provided
        if (dto.getTestReportSamplerIds() != null) {
            List<User> samplers = userRepository.findAllById(dto.getTestReportSamplerIds());
            List<TestReportSampler> testReportSamplers = samplers.stream()
                    .map(user -> TestReportSampler.builder()
                            .testReport(existing)
                            .user(user)
                            .build())
                    .toList();

            existing.getTestReportSamplers().clear();
            existing.getTestReportSamplers().addAll(testReportSamplers);
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
