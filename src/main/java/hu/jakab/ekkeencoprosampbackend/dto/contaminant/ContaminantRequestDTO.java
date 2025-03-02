package hu.jakab.ekkeencoprosampbackend.dto.contaminant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaminantRequestDTO {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String description;

    @NotNull(message = "Contaminant group ID cannot be null")
    private Long contaminantGroupId;

}
