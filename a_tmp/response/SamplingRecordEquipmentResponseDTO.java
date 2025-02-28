package hu.jakab.ekkeencoprosampbackend.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SamplingRecordEquipmentResponseDTO {

    private Long id;
    private Long samplingRecordId;
    private Long equipmentId;
    private String equipmentName; // Extra field for convenience
    private String remarks;
}
