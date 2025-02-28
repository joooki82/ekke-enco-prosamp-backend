package hu.jakab.ekkeencoprosampbackend.dto.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationCreatedDTO {
    private Long id;
    private String name;
    private String city;
    private String address;
    private String contactPerson;
    private String email;
    private String phone;
}
