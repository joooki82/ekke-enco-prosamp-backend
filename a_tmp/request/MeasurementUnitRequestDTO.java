package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasurementUnitRequestDTO {
    @NotBlank(message = "Unit code cannot be empty")
    private String unitCode;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotBlank(message = "Unit category cannot be empty")
    private String unitCategory;

    private Long baseUnitId; // Reference to another MeasurementUnit (optional)
    private Double conversionFactor;
    private String standardBody;
}
