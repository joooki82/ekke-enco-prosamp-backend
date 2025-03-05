package hu.jakab.ekkeencoprosampbackend.dto.sample;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleIdentifierDTO {
    
    private Long id;
    private String sampleIdentifier;

}
