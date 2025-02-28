package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandardRequestDTO {
    @NotBlank(message = "Identifier cannot be empty")
    private String identifier;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String description;
}
