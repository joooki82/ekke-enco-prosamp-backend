package hu.jakab.ekkeencoprosampbackend.dto.contaminantGroup;

import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantListItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
