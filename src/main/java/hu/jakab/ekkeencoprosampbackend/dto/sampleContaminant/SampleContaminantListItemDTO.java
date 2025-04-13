package hu.jakab.ekkeencoprosampbackend.dto.sampleContaminant;

import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleListItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleContaminantListItemDTO {

    private Long id;
    private SampleListItemDTO sample;
    private ContaminantListItemDTO contaminant;

}
