package hu.jakab.ekkeencoprosampbackend.dto.project;

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
public class ProjectRequestDTO {
    private String projectNumber;
    private Long clientId;
    private String projectName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String description;
}
