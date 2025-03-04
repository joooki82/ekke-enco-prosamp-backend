package hu.jakab.ekkeencoprosampbackend.dto.sampleContaminant;

import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleListItemDTO;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleContaminantResponseDTO {

    private Long id;
    private SampleListItemDTO sample;
    private ContaminantListItemDTO contaminant;
    private LocalDateTime createdAt;
}
