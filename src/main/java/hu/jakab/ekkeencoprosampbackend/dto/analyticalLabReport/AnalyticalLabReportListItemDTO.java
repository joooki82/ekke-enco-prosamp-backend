package hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticalLabReportListItemDTO {
    private Long id;
    private String reportNumber;
    private LocalDate issueDate;
}
