package hu.jakab.ekkeencoprosampbackend.dto.sampleContaminant;

import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantListNameDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleIdentifierDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleListItemDTO;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleWithContaminantsDTO {

    private SampleIdentifierDTO sample;
    private List<ContaminantListNameDTO> contaminants;

}
