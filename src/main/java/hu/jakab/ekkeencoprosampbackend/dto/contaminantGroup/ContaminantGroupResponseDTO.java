package hu.jakab.ekkeencoprosampbackend.dto.contaminantGroup;

import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantListItemDTO;
import hu.jakab.ekkeencoprosampbackend.model.Contaminant;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaminantGroupResponseDTO {
    private Long id;
    private String name;
    private String description;
    private List<ContaminantListItemDTO> contaminants = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
