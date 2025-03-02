package hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport;

import hu.jakab.ekkeencoprosampbackend.dto.laboratory.LaboratoryListItemDTO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticalLabReportResponseDTO {
    private Long id;
    private String reportNumber;
    private LocalDate issueDate;
    private LaboratoryListItemDTO laboratory;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
