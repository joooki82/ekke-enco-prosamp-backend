package hu.jakab.ekkeencoprosampbackend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String contactPerson;
    private String email;
    private String phone;
    private String country;
    private String city;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
