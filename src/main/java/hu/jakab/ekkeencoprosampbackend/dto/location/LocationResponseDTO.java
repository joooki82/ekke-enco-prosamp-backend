package hu.jakab.ekkeencoprosampbackend.dto.location;

import hu.jakab.ekkeencoprosampbackend.dto.company.CompanyListItemDTO;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationResponseDTO {
    private Long id;
    private CompanyListItemDTO company;
    private String name;
    private String address;
    private String contactPerson;
    private String email;
    private String phone;
    private String country;
    private String city;
    private String postalCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
