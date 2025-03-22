package hu.jakab.ekkeencoprosampbackend.dto.standard;

import hu.jakab.ekkeencoprosampbackend.model.StandardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandardRequestDTO {

    @NotBlank(message = "Standard number cannot be blank")
    @Size(max = 255, message = "Standard number must be at most 255 characters long")
    private String standardNumber;

    @Size(max = 65535, message = "Description is too long")
    private String description;

    @NotNull(message = "Standard type cannot be null")
    private StandardType standardType;

    @NotBlank(message = "Identifier cannot be blank")
    @Size(max = 255, message = "Identifier must be at most 255 characters long")
    private String identifier;
}
