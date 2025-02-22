package hu.jakab.ekkeencoprosampbackend.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentResponseDTO {
    private Long id;
    private String identifier;
    private String name;
    private String type;
    private String description;
    private String serialNumber;
    private String manufacturer;
    private LocalDate calibrationDate;
    private LocalDate nextCalibrationDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
