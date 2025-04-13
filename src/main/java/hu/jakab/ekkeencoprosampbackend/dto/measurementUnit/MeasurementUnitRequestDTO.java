package hu.jakab.ekkeencoprosampbackend.dto.measurementUnit;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MeasurementUnitRequestDTO {

    private Long id;

    @NotBlank(message = "A mértékegység kód megadása kötelező.")
    private String unitCode;

    @NotBlank(message = "A leírás megadása kötelező.")
    private String description;

    @NotBlank(message = "A kategória megadása kötelező.")
    private String unitCategory;

    private Long baseUnitId;

    private BigDecimal conversionFactor;

    private String standardBody;
}
