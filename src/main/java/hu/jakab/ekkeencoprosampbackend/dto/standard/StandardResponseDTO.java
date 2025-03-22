package hu.jakab.ekkeencoprosampbackend.dto.standard;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandardResponseDTO {

    private Long id;
    private String standardNumber;
    private String description;
    private String standardType;
    private String standardTypeMagyar;
    private String identifier;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
