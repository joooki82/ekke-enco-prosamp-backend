package hu.jakab.ekkeencoprosampbackend.dto.sample;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleListItemDTO {
    
    private Long id;
    private String sampleIdentifier;
    private Long samplingRecordId;
    private String location;
    private String employeeName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;



}
