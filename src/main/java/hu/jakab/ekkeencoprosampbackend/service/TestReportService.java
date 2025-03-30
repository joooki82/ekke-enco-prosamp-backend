package hu.jakab.ekkeencoprosampbackend.service;


import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.TestReportMapper;
import hu.jakab.ekkeencoprosampbackend.model.*;
import hu.jakab.ekkeencoprosampbackend.repository.*;
import hu.jakab.ekkeencoprosampbackend.service.laTex.LaTeXReportService;
import hu.jakab.ekkeencoprosampbackend.service.laTex.LatexContentBuilder;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
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
    private final LaTeXReportService latexReportService;
    private final LatexContentBuilder latexContentBuilder;


    @Autowired
    public TestReportService(TestReportRepository repository, TestReportMapper mapper, ProjectRepository projectRepository, LocationRepository locationRepository, SamplingRecordDatM200Repository samplingRecordRepository, TestReportStandardRepository testReportStandardRepository, TestReportSamplerRepository testReportSamplerRepository, UserRepository userRepository, StandardRepository standardRepository, LaTeXReportService latexReportService, LatexContentBuilder latexContentBuilder) {
        this.repository = repository;
        this.mapper = mapper;
        this.projectRepository = projectRepository;
        this.locationRepository = locationRepository;
        this.samplingRecordRepository = samplingRecordRepository;
        this.testReportStandardRepository = testReportStandardRepository;
        this.testReportSamplerRepository = testReportSamplerRepository;
        this.userRepository = userRepository;
        this.standardRepository = standardRepository;
        this.latexReportService = latexReportService;
        this.latexContentBuilder = latexContentBuilder;
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
            Set<Long> newStandardIds = new HashSet<>(dto.getTestReportStandardIds());
            List<TestReportStandard> currentStandards = existing.getTestReportStandards();

            // Remove standards that are not in the new list
            currentStandards.removeIf(existingStandard ->
                    !newStandardIds.contains(existingStandard.getStandard().getId()));

            // Add new standards that do not already exist in the list
            for (Long standardId : newStandardIds) {
                boolean exists = currentStandards.stream()
                        .anyMatch(existingStandard -> existingStandard.getStandard().getId().equals(standardId));

                if (!exists) {
                    Standard standard = standardRepository.findById(standardId)
                            .orElseThrow(() -> new ResourceNotFoundException("Standard with ID " + standardId + " not found"));
                    currentStandards.add(TestReportStandard.builder()
                            .testReport(existing)
                            .standard(standard)
                            .build());
                }
            }
            existing.setTestReportStandards(currentStandards);
        }

        // Update related samplers if provided
        if (dto.getTestReportSamplerIds() != null) {
            Set<UUID> newSamplerIds = new HashSet<>(dto.getTestReportSamplerIds());
            List<TestReportSampler> currentSamplers = existing.getTestReportSamplers();

            // Remove samplers that are not in the new list
            currentSamplers.removeIf(existingSampler ->
                    !newSamplerIds.contains(existingSampler.getUser().getId()));

            // Add new samplers that do not already exist in the list
            for (UUID samplerId : newSamplerIds) {
                boolean exists = currentSamplers.stream()
                        .anyMatch(existingSampler -> existingSampler.getUser().getId().equals(samplerId));

                if (!exists) {
                    User sampler = userRepository.findById(samplerId)
                            .orElseThrow(() -> new ResourceNotFoundException("Sampler with ID " + samplerId + " not found"));
                    currentSamplers.add(TestReportSampler.builder()
                            .testReport(existing)
                            .user(sampler)
                            .build());
                }
            }
            existing.setTestReportSamplers(currentSamplers);
        }

        try {
            TestReport updatedTestReport = repository.save(existing);
            return mapper.toResponseDTO(updatedTestReport);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update TestReport (ID: {}): {}", id, e.getMessage());
            throw new RuntimeException("Update failed: Duplicate TestReport report number or standard detected");
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

    public byte[] generateReport(Long id) {
        // Fetch report data from the database
        TestReport testReport = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestReport with ID " + id + " not found"));

        // Prepare the data map for LaTeX placeholders
        Map<String, String> reportData = new HashMap<>();
        reportData.put("companyName", testReport.getSamplingRecord().getCompany().getName());
        reportData.put("city", testReport.getSamplingRecord().getSiteLocation().getCity());
        reportData.put("aimOfTest", testReport.getAimOfTest());
        reportData.put("reportNumber", testReport.getReportNumber());
        reportData.put("title", testReport.getTitle());
        reportData.put("projectNumber", testReport.getProject().getProjectNumber());
        reportData.put("approvedBy", testReport.getApprovedBy().getUsername());
        reportData.put("approvedByRole", testReport.getApprovedBy().getRole());
        reportData.put("issueDate", testReport.getIssueDate().toString());

        String samplersString = latexContentBuilder.generateSamplersList(testReport.getTestReportSamplers());
        reportData.put("samplers", samplersString);

        reportData.put("clientName", testReport.getProject().getClient().getName());
        reportData.put("clientAddress", testReport.getProject().getClient().getAddress());

        reportData.put("locationName", testReport.getLocation().getName());
        reportData.put("locationAddress", testReport.getLocation().getAddress());

        Client contact = testReport.getProject().getClient();
        String clientContactString = latexContentBuilder.generateClientContact(contact);
        reportData.put("clientContact", clientContactString);

        String samplingSchedule = latexContentBuilder.generateSamplingSchedule(testReport);
        reportData.put("samplingSchedule", samplingSchedule);

        String samplingDate = latexContentBuilder.formatDateInHungarian(testReport.getSamplingRecord().getSamplingDate().toLocalDate());
        reportData.put("samplingDate", samplingDate);
        reportData.put("temperature", latexContentBuilder.formatBigDecimal(testReport.getSamplingRecord().getTemperature(), 0));
        reportData.put("humidity", latexContentBuilder.formatBigDecimal(testReport.getSamplingRecord().getHumidity(), 0));
        reportData.put("pressure", latexContentBuilder.formatBigDecimal(testReport.getSamplingRecord().getPressure1(), 0));
        reportData.put("technology", testReport.getTechnology());
        reportData.put("samplingConditions", testReport.getSamplingConditionsDates());
        reportData.put("determinationOfPollutantConcentration", testReport.getDeterminationOfPollutantConcentration());


        List<Sample> samples = testReport.getSamplingRecord().getSamples();

        List<Sample> samplesAverage = samples.stream()
                .filter(sample -> "AK" .equals(sample.getSampleType()))
                .sorted(Comparator.comparing(Sample::getLocation)
                        .thenComparing(Sample::getSampleIdentifier)) // Sort by Location, then by Identifier
                .toList();
        reportData.put("sampleDetailsAverage", latexContentBuilder.generateSampleDetails(samplesAverage));

        List<Sample> samplesPeak = samples.stream()
                .filter(sample -> "CK" .equals(sample.getSampleType()))
                .sorted(Comparator.comparing(Sample::getLocation)
                        .thenComparing(Sample::getSampleIdentifier)) // Sort by Location, then by Identifier
                .toList();
        reportData.put("sampleDetailsPeak", latexContentBuilder.generateSampleDetails(samplesPeak));

        reportData.put("equipmentList", latexContentBuilder.generateEquipmentList(testReport));

        List<Standard> samplingStandards = testReport.getTestReportStandards().stream()
                .map(TestReportStandard::getStandard)
                .filter(standard -> standard.getStandardType().toString().contains("SAMPLING"))
                .collect(Collectors.toList());

        reportData.put("samplingStandards", latexContentBuilder.generateStandardList(samplingStandards));

        List<Standard> encotechStandards = testReport.getTestReportStandards().stream()
                .map(TestReportStandard::getStandard)
                .filter(standard -> standard.getLaboratoryStandards().stream()
                        .map(LaboratoryStandard::getLaboratory)
                        .anyMatch(laboratory -> laboratory.getName().contains("Encotech")))
                .filter(standard -> standard.getStandardType().toString().contains("ANALYSES"))
                .collect(Collectors.toList());

        reportData.put("encotechStandards", latexContentBuilder.generateStandardList(encotechStandards));

        List<Standard> balintStandards = testReport.getTestReportStandards().stream()
                .map(TestReportStandard::getStandard)
                .filter(standard -> standard.getLaboratoryStandards().stream()
                        .map(LaboratoryStandard::getLaboratory)
                        .anyMatch(laboratory -> laboratory.getName().contains("Bálint")))
                .filter(standard -> standard.getStandardType().toString().contains("ANALYSES"))
                .collect(Collectors.toList());

        reportData.put("balintStandards", latexContentBuilder.generateStandardList(balintStandards));

        reportData.put("averageConcentration", latexContentBuilder.generateSampleResults(samplesAverage));

        reportData.put("peakConcentration", latexContentBuilder.generateSampleResults(samplesPeak));

        testReport.setIssueDate(LocalDate.now());

        reportData.put("issueDate", testReport.getIssueDate().toString());

        reportData.put("preparedBy", testReport.getPreparedBy().getUsername());

        reportData.put("preparedByRole", testReport.getPreparedBy().getRole());

        reportData.put("checkedBy", testReport.getCheckedBy().getUsername());

        reportData.put("checkedByRole", testReport.getCheckedBy().getRole());

        try {
            return latexReportService.generatePdfReport(reportData);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to generate the report", e);
        }
    }


}

