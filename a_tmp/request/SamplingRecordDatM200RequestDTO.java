package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SamplingRecordDatM200RequestDTO {

    @NotNull(message = "Project ID cannot be null")
    private Long projectId;

    @NotBlank(message = "Record identifier cannot be empty")
    private String recordIdentifier;

    private String location;
    private String operator;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String remarks;
}
