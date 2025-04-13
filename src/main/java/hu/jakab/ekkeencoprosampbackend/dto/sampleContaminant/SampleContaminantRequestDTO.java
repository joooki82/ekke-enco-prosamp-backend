package hu.jakab.ekkeencoprosampbackend.dto.sampleContaminant;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleContaminantRequestDTO {

    @NotNull(message = "A minta azonosító megadása kötelező.")
    private Long sampleId;

    @NotNull(message = "A szennyező anyag azonosító megadása kötelező.")
    private Long contaminantId;
}
