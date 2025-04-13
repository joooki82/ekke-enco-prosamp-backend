package hu.jakab.ekkeencoprosampbackend.dto.equipment;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EquipmentCreatedDTO {
    private Long id;
    private String name;
    private String identifier;
    private String description;
    private String manufacturer;
    private String type;
    private String serialNumber;
    private String measuringRange;
    private String resolution;
    private String accuracy;
    private LocalDate calibrationDate;
    private LocalDate nextCalibrationDate;
}
