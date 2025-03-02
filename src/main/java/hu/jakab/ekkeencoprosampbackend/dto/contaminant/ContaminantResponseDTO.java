package hu.jakab.ekkeencoprosampbackend.dto.contaminant;

import hu.jakab.ekkeencoprosampbackend.dto.contaminantGroup.ContaminantGroupListItemDTO;
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
    private ContaminantGroupListItemDTO contaminantGroup;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
