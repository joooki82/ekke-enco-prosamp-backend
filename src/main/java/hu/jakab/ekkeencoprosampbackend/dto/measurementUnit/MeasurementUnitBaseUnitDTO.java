package hu.jakab.ekkeencoprosampbackend.dto.measurementUnit;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeasurementUnitBaseUnitDTO {
    private Long id;
    private String unitCode;
    private String description;
}
