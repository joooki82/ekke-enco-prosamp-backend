package hu.jakab.ekkeencoprosampbackend.dto.project;

import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportListItemDTO;
import hu.jakab.ekkeencoprosampbackend.model.Client;
import hu.jakab.ekkeencoprosampbackend.model.TestReport;
import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDTO {
    private Long id;
    private String projectNumber;
    private Long clientId;
    private String clientName;
    private String projectName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private List<TestReportListItemDTO> testReports = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
