package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticalLabReportRequestDTO {
    @NotBlank(message = "Report number cannot be empty")
    private String reportNumber;

    @NotNull(message = "Issue date cannot be null")
    private LocalDate issueDate;

    @NotNull(message = "Laboratory ID is required")
    private Long laboratoryId;
}
