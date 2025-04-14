package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ReportGenerationException;
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
        logger.info("Creating new TestReport with reportNumber: '{}'", dto.getReportNumber());

        if (repository.existsByReportNumber(dto.getReportNumber())) {
            logger.warn("Duplicate report number: '{}'", dto.getReportNumber());
            throw new DuplicateResourceException("A TestReport with report number '" + dto.getReportNumber() + "' already exists.");
        }

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project with ID " + dto.getProjectId() + " not found"));
        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location with ID " + dto.getLocationId() + " not found"));
        SamplingRecordDatM200 samplingRecord = samplingRecordRepository.findById(dto.getSamplingRecordId())
                .orElseThrow(() -> new ResourceNotFoundException("Sampling record with ID " + dto.getSamplingRecordId() + " not found"));

        TestReport testReport = mapper.toEntity(dto);
        testReport.setProject(project);
        testReport.setLocation(location);
        testReport.setSamplingRecord(samplingRecord);
        testReport.setReportStatus(TestReportStatus.valueOf(dto.getReportStatus()));

        try {
            TestReport savedTestReport = repository.save(testReport);

            if (dto.getTestReportStandardIds() != null) {
                List<Standard> standards = standardRepository.findAllById(dto.getTestReportStandardIds());
                List<TestReportStandard> linkedStandards = standards.stream()
                        .map(s -> TestReportStandard.builder().testReport(savedTestReport).standard(s).build())
                        .toList();
                testReportStandardRepository.saveAll(linkedStandards);
            }

            if (dto.getTestReportSamplerIds() != null && !dto.getTestReportSamplerIds().isEmpty()) {
                List<User> samplers = userRepository.findAllById(dto.getTestReportSamplerIds());
                List<TestReportSampler> linkedSamplers = samplers.stream()
                        .map(u -> TestReportSampler.builder().testReport(savedTestReport).user(u).build())
                        .toList();
                testReportSamplerRepository.saveAll(linkedSamplers);
            }
            logger.info("TestReport created successfully with ID: {}", savedTestReport.getId());
            return mapper.toCreatedDTO(savedTestReport);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to save TestReport with reportNumber '{}': {}", dto.getReportNumber(), e.getMessage(), e);
            throw new DuplicateResourceException("Failed to create TestReport: Duplicate constraint on reportNumber or linked entities.");
        }
    }

    @Transactional
    public TestReportResponseDTO update(Long id, TestReportRequestDTO dto) {
        logger.info("Updating TestReport with ID: {}", id);

        TestReport existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TestReport with ID " + id + " not found."));

        if (dto.getReportNumber() != null && !dto.getReportNumber().equals(existing.getReportNumber())) {
            if (repository.existsByReportNumber(dto.getReportNumber())) {
                logger.warn("Update conflict: Duplicate report number '{}'", dto.getReportNumber());
                throw new DuplicateResourceException("A TestReport with report number '" + dto.getReportNumber() + "' already exists.");
            }
            existing.setReportNumber(dto.getReportNumber());
        }

        mapper.updateEntityFromDTO(dto, existing);

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

        updateLinkedStandards(existing, dto);
        updateLinkedSamplers(existing, dto);


        try {
            TestReport updatedTestReport = repository.save(existing);
            logger.info("TestReport updated successfully with ID: {}", updatedTestReport.getId());
            return mapper.toResponseDTO(updatedTestReport);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to update TestReport with ID {}: {}", id, e.getMessage(), e);
            throw new DuplicateResourceException("Update failed: Constraint violation on report number or relationships.");
        }
    }

    private void updateLinkedStandards(TestReport existing, TestReportRequestDTO dto) {
        if (dto.getTestReportStandardIds() != null) {
            Set<Long> newStandardIds = new HashSet<>(dto.getTestReportStandardIds());
            List<TestReportStandard> currentStandards = existing.getTestReportStandards();

            currentStandards.removeIf(existingStandard ->
                    !newStandardIds.contains(existingStandard.getStandard().getId()));

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
    }

    private void updateLinkedSamplers(TestReport existing, TestReportRequestDTO dto) {
        if (dto.getTestReportSamplerIds() != null) {
            Set<UUID> newSamplerIds = new HashSet<>(dto.getTestReportSamplerIds());
            List<TestReportSampler> currentSamplers = existing.getTestReportSamplers();

            currentSamplers.removeIf(existingSampler ->
                    !newSamplerIds.contains(existingSampler.getUser().getId()));

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
        logger.info("Generating report for TestReport ID: {}", id);

        TestReport testReport = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Report generation failed: TestReport with ID {} not found", id);
                    return new ResourceNotFoundException("TestReport with ID " + id + " not found.");
                });

        try {
            Map<String, String> reportData = buildReportData(testReport);
            return latexReportService.generatePdfReport(reportData);

        } catch (IOException | InterruptedException e) {
            logger.error("Report generation failed for TestReport ID {} due to system error: {}", id, e.getMessage(), e);
            throw new ReportGenerationException("Failed to generate report for TestReport ID " + id, e);
        } catch (Exception e) {
            logger.error("Unexpected error during report generation for TestReport ID {}: {}", id, e.getMessage(), e);
            throw new ReportGenerationException("Unexpected error during report generation", e);
        }
    }

    private Map<String, String> buildReportData(TestReport testReport) {
        Map<String, String> reportData = new HashMap<>();

        reportData.put("companyName", testReport.getSamplingRecord().getCompany().getName());
        reportData.put("city", testReport.getSamplingRecord().getSiteLocation().getCity());
        reportData.put("aimOfTest", testReport.getAimOfTest());
        reportData.put("reportNumber", testReport.getReportNumber());
        reportData.put("title", testReport.getTitle());
        reportData.put("projectNumber", testReport.getProject().getProjectNumber());
        reportData.put("approvedBy", testReport.getApprovedBy().getLastName() + " " + testReport.getApprovedBy().getFirstName());
        reportData.put("issueDate", testReport.getIssueDate().toString());

        String samplersString = latexContentBuilder.generateSamplersList(testReport.getTestReportSamplers());
        reportData.put("samplers", samplersString);

        reportData.put("clientName", testReport.getProject().getClient().getName());
        reportData.put("clientAddress", testReport.getProject().getClient().getAddress());

        reportData.put("locationName", testReport.getLocation().getName());
        reportData.put("locationAddress", testReport.getLocation().getAddress());

        Client contact = testReport.getProject().getClient();
        reportData.put("clientContact", latexContentBuilder.generateClientContact(contact));

        reportData.put("samplingSchedule", latexContentBuilder.generateSamplingSchedule(testReport));

        reportData.put("samplingDate", latexContentBuilder.formatDateInHungarian(testReport.getSamplingRecord().getSamplingDate().toLocalDate()));
        reportData.put("temperature", latexContentBuilder.formatBigDecimal(testReport.getSamplingRecord().getTemperature(), 0));
        reportData.put("humidity", latexContentBuilder.formatBigDecimal(testReport.getSamplingRecord().getHumidity(), 0));
        reportData.put("pressure", latexContentBuilder.formatBigDecimal(testReport.getSamplingRecord().getPressure1(), 0));

        reportData.put("technology", testReport.getTechnology());
        reportData.put("samplingConditions", testReport.getSamplingConditionsDates());
        reportData.put("determinationOfPollutantConcentration", testReport.getDeterminationOfPollutantConcentration());

        List<Sample> samples = testReport.getSamplingRecord().getSamples();

        List<Sample> samplesAverage = samples.stream()
                .filter(sample -> "AK".equals(sample.getSampleType()))
                .sorted(Comparator.comparing(Sample::getLocation).thenComparing(Sample::getSampleIdentifier))
                .toList();
        reportData.put("sampleDetailsAverage", latexContentBuilder.generateSampleDetails(samplesAverage));

        List<Sample> samplesPeak = samples.stream()
                .filter(sample -> "CK".equals(sample.getSampleType()))
                .sorted(Comparator.comparing(Sample::getLocation).thenComparing(Sample::getSampleIdentifier))
                .toList();
        reportData.put("sampleDetailsPeak", latexContentBuilder.generateSampleDetails(samplesPeak));

        reportData.put("equipmentList", latexContentBuilder.generateEquipmentList(testReport));

        List<Standard> samplingStandards = testReport.getTestReportStandards().stream()
                .map(TestReportStandard::getStandard)
                .filter(standard -> standard.getStandardType().toString().contains("SAMPLING"))
                .toList();
        reportData.put("samplingStandards", latexContentBuilder.generateStandardList(samplingStandards));

        List<Standard> encotechStandards = testReport.getTestReportStandards().stream()
                .map(TestReportStandard::getStandard)
                .filter(standard -> standard.getLaboratoryStandards().stream()
                        .map(LaboratoryStandard::getLaboratory)
                        .anyMatch(lab -> lab.getName().contains("Encotech")))
                .filter(standard -> standard.getStandardType().toString().contains("ANALYSES"))
                .toList();
        reportData.put("encotechStandards", latexContentBuilder.generateStandardList(encotechStandards));

        List<Standard> balintStandards = testReport.getTestReportStandards().stream()
                .map(TestReportStandard::getStandard)
                .filter(standard -> standard.getLaboratoryStandards().stream()
                        .map(LaboratoryStandard::getLaboratory)
                        .anyMatch(lab -> lab.getName().contains("Bálint")))
                .filter(standard -> standard.getStandardType().toString().contains("ANALYSES"))
                .toList();
        reportData.put("balintStandards", latexContentBuilder.generateStandardList(balintStandards));

        reportData.put("averageConcentration", latexContentBuilder.generateSampleResults(samplesAverage));
        reportData.put("peakConcentration", latexContentBuilder.generateSampleResults(samplesPeak));

        testReport.setIssueDate(LocalDate.now());
        reportData.put("issueDate", testReport.getIssueDate().toString());

        reportData.put("preparedBy", testReport.getPreparedBy().getLastName() + " " + testReport.getPreparedBy().getFirstName());
        reportData.put("checkedBy", testReport.getCheckedBy().getLastName() + " " + testReport.getCheckedBy().getFirstName());

        return reportData;
    }


}
