package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdjustmentMethodRequestDTO {
    @NotBlank(message = "Code cannot be empty")
    private String code;

    @NotBlank(message = "Description cannot be empty")
    private String description;
}
