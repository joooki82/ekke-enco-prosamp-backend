package hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport;

import hu.jakab.ekkeencoprosampbackend.dto.laboratory.LaboratoryListItemDTO;
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
public class AnalyticalLabReportListItemDTO {
    private Long id;
    private String reportNumber;
    private LocalDate issueDate;
}
