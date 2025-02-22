package hu.jakab.ekkeencoprosampbackend.dto.response;

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
    private Long laboratoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
