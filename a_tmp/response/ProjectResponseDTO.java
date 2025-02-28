package hu.jakab.ekkeencoprosampbackend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Long clientId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
