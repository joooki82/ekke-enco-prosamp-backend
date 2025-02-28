package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SamplingTypeRequestDTO {
    @NotBlank(message = "Code cannot be empty")
    private String code;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String description;
}
