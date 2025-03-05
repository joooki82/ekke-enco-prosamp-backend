package hu.jakab.ekkeencoprosampbackend.dto.sampleContaminant;

import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleListItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleContaminantCreatedDTO {

    private Long id;
    private SampleListItemDTO sample;
    private ContaminantListItemDTO contaminant;
}
