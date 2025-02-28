package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleContaminantRequestDTO {

    @NotNull(message = "Sample ID cannot be null")
    private Long sampleId;

    @NotNull(message = "Contaminant ID cannot be null")
    private Long contaminantId;
}
