package hu.jakab.ekkeencoprosampbackend.dto.samplingType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SamplingTypeListItemDTO {

    private Long id;
    private String code;
    private String description;
}
