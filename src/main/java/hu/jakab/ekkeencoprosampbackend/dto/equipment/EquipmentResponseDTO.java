package hu.jakab.ekkeencoprosampbackend.dto.equipment;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EquipmentResponseDTO {
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
