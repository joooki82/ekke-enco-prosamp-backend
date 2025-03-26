package hu.jakab.ekkeencoprosampbackend.dto.sampleContaminant;

import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleIdentifierDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleWithSampleContaminantsDTO {

    private SampleIdentifierDTO sample;
    private List<SampleContaminantListItem2DTO> sampleContaminants;

}
