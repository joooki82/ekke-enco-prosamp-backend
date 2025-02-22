package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaminantRequestDTO {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String chemicalFormula;
    private String description;
}
