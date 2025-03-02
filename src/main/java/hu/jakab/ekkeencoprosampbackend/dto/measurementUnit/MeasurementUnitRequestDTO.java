package hu.jakab.ekkeencoprosampbackend.dto.measurementUnit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MeasurementUnitRequestDTO {
    private Long id; // Nullable for creation, required for updates

    @NotBlank
    private String unitCode; // e.g., "mg/m³", "ppm", "µg/L"

    @NotBlank
    private String description; // e.g., "Milligrams per cubic meter"

    @NotBlank
    private String unitCategory; // e.g., "Concentration", "Mass", "Volume"

    private Long baseUnitId; // ID reference to base unit (nullable)

    private Double conversionFactor; // Factor to convert to base unit

    private String standardBody; // e.g., "SI", "ISO", "ASTM", "EPA"
}
