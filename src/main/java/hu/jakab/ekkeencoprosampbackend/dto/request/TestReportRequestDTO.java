package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestReportRequestDTO {

    @NotBlank(message = "Report number cannot be empty")
    private String reportNumber;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    private UUID approvedBy;
    private UUID preparedBy;
    private UUID checkedBy;

    private String aimOfTest;
    private String technology;
    private String samplingConditionsDates;
    private String determinationOfPollutantConcentration;

    @NotNull(message = "Project ID cannot be null")
    private Long projectId;

    @NotNull(message = "Client ID cannot be null")
    private Long clientId;

    @NotNull(message = "Location ID cannot be null")
    private Long locationId;

    @NotNull(message = "Sampling record ID cannot be null")
    private Long samplingRecordId;

    @NotNull(message = "Issue date cannot be null")
    private LocalDate issueDate;

    @NotBlank(message = "Report status cannot be empty")
    private String reportStatus;
}
