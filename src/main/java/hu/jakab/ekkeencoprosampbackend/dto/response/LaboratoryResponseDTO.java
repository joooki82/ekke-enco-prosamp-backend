package hu.jakab.ekkeencoprosampbackend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaboratoryResponseDTO {
    private Long id;
    private String name;
    private String accreditation;
    private String contactEmail;
    private String phone;
    private String address;
    private String website;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
