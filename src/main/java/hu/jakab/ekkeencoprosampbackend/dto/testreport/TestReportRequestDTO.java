package hu.jakab.ekkeencoprosampbackend.dto.testreport;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestReportRequestDTO {

    @NotBlank(message = "Report number cannot be blank")
    @Size(max = 50, message = "Report number must be at most 50 characters long")
    private String reportNumber;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title must be at most 255 characters long")
    private String title;

    private UUID approvedBy;
    private UUID preparedBy;
    private UUID checkedBy;

    private String aimOfTest;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    private List<Long> testReportStandardIds;
    private List<UUID> testReportSamplerIds;

    @NotNull(message = "Location ID is required")
    private Long locationId;

    @NotNull(message = "Sampling Record ID is required")
    private Long samplingRecordId;

    private String technology;
    private String samplingConditionsDates;
    private String determinationOfPollutantConcentration;

    @NotNull(message = "Issue date is required")
    private LocalDate issueDate;

    @NotNull(message = "Report status is required")
    private String reportStatus;

}
