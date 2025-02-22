package hu.jakab.ekkeencoprosampbackend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleContaminantResponseDTO {

    private Long id;
    private Long sampleId;
    private Long contaminantId;
    private String contaminantName; // Additional field for better readability
    private LocalDateTime createdAt;
}
