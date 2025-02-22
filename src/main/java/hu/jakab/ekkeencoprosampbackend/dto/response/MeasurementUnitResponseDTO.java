package hu.jakab.ekkeencoprosampbackend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasurementUnitResponseDTO {
    private Long id;
    private String unitCode;
    private String description;
    private String unitCategory;
    private String standardBody;
    private Double conversionFactor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
