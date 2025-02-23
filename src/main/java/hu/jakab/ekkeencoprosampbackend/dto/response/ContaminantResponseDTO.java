package hu.jakab.ekkeencoprosampbackend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaminantResponseDTO {
    private Long id;
    private String name;
    private String description;

    private ContaminantGroupResponseDTO contaminantGroup;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
