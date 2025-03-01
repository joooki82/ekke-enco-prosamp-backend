package hu.jakab.ekkeencoprosampbackend.dto.testreport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestReportListItemDTO {
    
    private Long id;
    private String reportNumber;
    private String title;

    private UUID approvedBy;
    private UUID preparedBy;
    private UUID checkedBy;

    private String aimOfTest;
    private String technology;
    private String samplingConditionsDates;
    private String determinationOfPollutantConcentration;

    private Long projectId;
    private Long locationId;
    private Long samplingRecordId;

    private LocalDate issueDate;
    private String reportStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
