package hu.jakab.ekkeencoprosampbackend.dto.company;

import hu.jakab.ekkeencoprosampbackend.dto.location.LocationListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.location.LocationResponseDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<LocationListItemDTO> locations;
}
