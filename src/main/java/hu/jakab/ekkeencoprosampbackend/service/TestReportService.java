package hu.jakab.ekkeencoprosampbackend.service;


import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.TestReportMapper;
import hu.jakab.ekkeencoprosampbackend.model.*;
import hu.jakab.ekkeencoprosampbackend.repository.*;
import hu.jakab.ekkeencoprosampbackend.service.utils.LaTeXReportService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
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

    @Autowired
    public TestReportService(TestReportRepository repository, TestReportMapper mapper, ProjectRepository projectRepository, LocationRepository locationRepository, SamplingRecordDatM200Repository samplingRecordRepository, TestReportStandardRepository testReportStandardRepository, TestReportSamplerRepository testReportSamplerRepository, UserRepository userRepository, StandardRepository standardRepository, LaTeXReportService latexReportService) {
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

        String samplersString = generateSamplersList(testReport.getTestReportSamplers());
        reportData.put("samplers", samplersString);

        reportData.put("clientName", testReport.getProject().getClient().getName());
        reportData.put("clientAddress", testReport.getProject().getClient().getAddress());

        reportData.put("locationName", testReport.getLocation().getName());
        reportData.put("locationAddress", testReport.getLocation().getAddress());

        Client contact = testReport.getProject().getClient();
        String clientContactString = generateClientContact(contact);
        reportData.put("clientContact", clientContactString);

        String samplingSchedule = generateSamplingSchedule(testReport);
        reportData.put("samplingSchedule", samplingSchedule);

        String samplingDate = formatDateInHungarian(testReport.getSamplingRecord().getSamplingDate().toLocalDate());
        reportData.put("samplingDate", samplingDate);
        reportData.put("temperature", formatBigDecimal(testReport.getSamplingRecord().getTemperature(), 0));
        reportData.put("humidity", formatBigDecimal(testReport.getSamplingRecord().getHumidity(), 0));
        reportData.put("pressure", formatBigDecimal(testReport.getSamplingRecord().getPressure1(), 0));
        reportData.put("technology", testReport.getTechnology());
        reportData.put("samplingConditions", testReport.getSamplingConditionsDates());

        List<Sample> samples = testReport.getSamplingRecord().getSamples();
        List<Sample> samplesAverage = samples.stream()
                .filter(sample -> "AK".equals(sample.getSampleType()))
                .sorted(Comparator.comparing(Sample::getLocation)
                        .thenComparing(Sample::getSampleIdentifier)) // Sort by Location, then by Identifier
                .toList();
        reportData.put("sampleDetailsAverage", generateSampleDetails(samplesAverage));

        List<Sample> samplesPeak = samples.stream()
                .filter(sample -> "CK".equals(sample.getSampleType()))
                .sorted(Comparator.comparing(Sample::getLocation)
                        .thenComparing(Sample::getSampleIdentifier)) // Sort by Location, then by Identifier
                .toList();
        reportData.put("sampleDetailsPeak", generateSampleDetails(samplesPeak));


        // Generate the LaTeX-based PDF and return byte array
        try {
            return latexReportService.generatePdfReport(reportData);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to generate the report", e);
        }
    }

    private String generateSamplersList(List<TestReportSampler> samplers) {
        StringBuilder samplersTable = new StringBuilder(); // Create new instance every time the method is invoked

        for (TestReportSampler sampler : samplers) {
            samplersTable.append("& ")
                    .append(sampler.getUser().getUsername())
                    .append(", ")
                    .append(sampler.getUser().getRole())
                    .append(" \\\\ ");
        }

        return samplersTable.toString(); // Convert to a string before returning
    }

    private String generateClientContact(Client client) {
        if (client == null) {
            return "";
        }
        return "& " + client.getName() + " \\\\  & " + client.getPhone();
    }

    public String generateSamplingSchedule(TestReport testReport) {
        if (testReport == null || testReport.getSamplingRecord() == null) {
            return "No sampling record available.";
        }

        List<Sample> samples = testReport.getSamplingRecord().getSamples();
        if (samples.isEmpty()) {
            return "No samples recorded.";
        }

        // Group samples by the date of sampling
        Map<LocalDate, List<Sample>> groupedByDate = samples.stream()
                .collect(Collectors.groupingBy(sample -> sample.getStartTime().toLocalDate()));

        // Generate formatted schedule
        StringBuilder schedule = new StringBuilder();

        groupedByDate.forEach((date, sampleList) -> {
            int minHour = sampleList.stream()
                    .mapToInt(sample -> sample.getStartTime().getHour())
                    .min().orElse(0);

            int maxHour = sampleList.stream()
                    .mapToInt(sample -> sample.getEndTime().getHour())
                    .max().orElse(0);

            // Format date in Hungarian style (e.g., "2024. április 3.")
            String formattedDate = formatDateInHungarian(date);

            schedule.append("& ")
                    .append(formattedDate)
                    .append(" & ")
                    .append(minHour)
                    .append("-")
                    .append(maxHour)
                    .append(" óra között \\\\");
        });

        return schedule.toString().trim();
    }

    private String formatDateInHungarian(LocalDate date) {
        return date.getYear() + ". " +
                date.getMonth().getDisplayName(TextStyle.FULL, new Locale("hu")) + " " +
                date.getDayOfMonth() + ".";
    }

    public String generateSampleDetails(List<Sample> samples) {
        if (samples == null || samples.isEmpty()) {
            return "No sample data available.";

        }
        StringBuilder sampleDetails = new StringBuilder();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.ENGLISH);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm"); // Fixed pattern

        for (Sample sample : samples) {
            // Extract and format data
            String formattedDate = sample.getStartTime().format(dateFormatter);
            String formattedStartTime = sample.getStartTime().format(timeFormatter)
                    .replace(":", "\\textsuperscript{") + "}"; // Fix LaTeX superscript
            String formattedEndTime = sample.getEndTime().format(timeFormatter)
                    .replace(":", "\\textsuperscript{") + "}";

            // Collect contaminant groups into a comma-separated string
            String contaminantGroups = sample.getSampleContaminants().stream()
                    .map(SampleContaminant::getContaminant)
                    .map(Contaminant::getContaminantGroup)
                    .distinct()
                    .map(ContaminantGroup::getName)
                    .collect(Collectors.joining(", "));


            sampleDetails.append("\\begin{minipage}{3.5cm} ")
                    .append("\\centering \\vspace{3pt} ")
                    .append("\\textbf{"+ sample.getSampleIdentifier() + " /} \\\\ \\textit{" +contaminantGroups+ "} \\vspace{3pt}")
                    .append("\\end{minipage} & ")
                    .append("\\begin{minipage}{2cm} ")
                    .append("\\centering ")
                    .append( formattedDate + "\\\\ " + formattedStartTime + " - " + formattedEndTime)
                    .append("\\end{minipage} & ")
                    .append("\\begin{minipage}{3.5cm} ")
                    .append("\\centering \\vspace{3pt}  ")
                    .append(sample.getLocation())
                    .append("\\end{minipage} & ")
                    .append("\\begin{minipage}{2cm} \\centering "+ (sample.getEmployeeName() != null ? sample.getEmployeeName() : "-") +" \\end{minipage} & ")
                    .append("\\begin{minipage}{1cm} \\centering "+ (sample.getTemperature() != null ? sample.getTemperature() : "-") +" \\end{minipage} & ")
                    .append("\\begin{minipage}{1cm} \\centering "+ (sample.getHumidity() != null ? sample.getHumidity() : "-") +" \\end{minipage} \\\\ ")
                    .append("\\hline");

        }

        return sampleDetails.toString();
    }

    private String formatBigDecimal(BigDecimal value, int scale) {
        if (value == null) return "-"; // Return placeholder if value is missing
        return value.setScale(scale, RoundingMode.HALF_UP).toString(); // Round to 1 decimal place
    }

    public String generateEquipmentList(SamplingRecordDatM200 samplingReport) {

        List<Equipment> equipments = samplingReport.getEquipmentList();

        if (equipments.isEmpty()) {
            return "No equipment available.";
        }

        StringBuilder latexBuilder = new StringBuilder();

        latexBuilder.append("\\section*{Equipment List}\n")
                .append("\\vspace{1.0em} % Small space for better readability\n")
                .append("\\noindent\n")
                .append("\\centering\n")
                .append("\\begin{tabular}{ p{5.5cm} p{8cm} } \n")
                .append("\\hline\n")
                .append("\\textbf{Name} & \\textbf{Manufacturer} & \\textbf{Identifier} & \\textbf{Type} & \\textbf{Measuring Range} & \\textbf{Resolution} & \\textbf{Accuracy} \\\\\n")
                .append("\\hline\n");

        // Append each equipment data
        for (Equipment equipment : equipments) {
            latexBuilder.append(equipment.getName()).append(" & ")
                    .append(equipment.getManufacturer() != null ? equipment.getManufacturer() : "N/A").append(" & ")
                    .append(equipment.getIdentifier()).append(" & ")
                    .append(equipment.getType() != null ? equipment.getType() : "N/A").append(" & ")
                    .append(equipment.getMeasuringRange() != null ? equipment.getMeasuringRange() : "N/A").append(" & ")
                    .append(equipment.getResolution() != null ? equipment.getResolution() : "N/A").append(" & ")
                    .append(equipment.getAccuracy() != null ? equipment.getAccuracy() : "N/A").append(" \\\\\n");
        }

        latexBuilder.append("\\hline\n")
                .append("\\end{tabular}\n");

        return latexBuilder.toString();
    }


}
