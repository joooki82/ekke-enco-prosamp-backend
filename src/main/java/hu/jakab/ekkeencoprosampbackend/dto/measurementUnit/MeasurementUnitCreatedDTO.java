package hu.jakab.ekkeencoprosampbackend.dto.measurementUnit;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MeasurementUnitCreatedDTO {
    private Long id;
    private String unitCode;
    private String description;
    private String unitCategory;
    private BigDecimal conversionFactor;
    private String standardBody;

}
