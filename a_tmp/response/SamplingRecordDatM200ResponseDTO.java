package hu.jakab.ekkeencoprosampbackend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SamplingRecordDatM200ResponseDTO {
    
    private Long id;
    private Long projectId;
    private String recordIdentifier;
    private String location;
    private String operator;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
