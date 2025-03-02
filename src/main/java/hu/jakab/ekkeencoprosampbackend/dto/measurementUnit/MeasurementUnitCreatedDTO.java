package hu.jakab.ekkeencoprosampbackend.dto.measurementUnit;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeasurementUnitCreatedDTO {
    private Long id;
    private String unitCode;
    private String description;
    private String unitCategory;
    private Double conversionFactor;
    private String standardBody;

}
