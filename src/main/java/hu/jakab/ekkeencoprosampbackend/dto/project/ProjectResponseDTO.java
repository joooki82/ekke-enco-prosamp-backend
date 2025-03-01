package hu.jakab.ekkeencoprosampbackend.dto.project;

import hu.jakab.ekkeencoprosampbackend.model.Client;
import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDTO {
    private Long id;
    private String projectNumber;
    private String projectName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Long clientId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
