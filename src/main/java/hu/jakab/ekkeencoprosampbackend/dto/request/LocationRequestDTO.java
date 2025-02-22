package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationRequestDTO {
    private Long companyId;

    @NotBlank(message = "Location name cannot be empty")
    private String name;

    private String address;

    @NotBlank(message = "Contact person cannot be empty")
    private String contactPerson;

    @Email(message = "Invalid email format")
    private String email;

    private String phone;
    private String country;
    private String city;
    private String postalCode;
}
