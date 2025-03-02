package hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticalLabReportCreatedDTO {
    private Long id;
    private String reportNumber;
    private LocalDate issueDate;
    private Long laboratoryId;
}
