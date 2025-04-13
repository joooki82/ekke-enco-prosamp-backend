package hu.jakab.ekkeencoprosampbackend.dto.samplingType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SamplingTypeCreatedDTO {

    private Long id;
    private String code;
    private String description;
}
