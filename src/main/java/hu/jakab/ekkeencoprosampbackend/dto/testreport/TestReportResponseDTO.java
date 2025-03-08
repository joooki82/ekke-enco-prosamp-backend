package hu.jakab.ekkeencoprosampbackend.dto.testreport;

import hu.jakab.ekkeencoprosampbackend.dto.location.LocationListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200.SamplingRecordDatM200ListItemDTO;
import hu.jakab.ekkeencoprosampbackend.model.SamplingRecordDatM200;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestReportResponseDTO {

    private Long id;
    private String reportNumber;
    private String title;
    private UUID approvedBy;
    private UUID preparedBy;
    private UUID checkedBy;
    private String aimOfTest;
    private ProjectListItemDTO project;
    private List<Long> testReportStandardIds;
    private List<Long> testReportSamplerIds;
    private LocationListItemDTO location;
    private SamplingRecordDatM200ListItemDTO samplingRecord;
    private String technology;
    private String samplingConditionsDates;
    private String determinationOfPollutantConcentration;
    private LocalDate issueDate;
    private String reportStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
