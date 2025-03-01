package hu.jakab.ekkeencoprosampbackend.dto.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectListItemDTO {
    private Long id;
    private String projectNumber;
    private String projectName;
    private String description;
    private String status;
}
