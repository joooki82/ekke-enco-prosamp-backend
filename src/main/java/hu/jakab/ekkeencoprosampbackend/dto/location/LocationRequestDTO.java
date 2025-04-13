package hu.jakab.ekkeencoprosampbackend.dto.location;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationRequestDTO {

    @NotNull(message = "A cég azonosítója kötelező.")
    private Long companyId;

    @NotBlank(message = "A telephely neve nem lehet üres.")
    private String name;

    private String address;

    @NotBlank(message = "A kapcsolattartó megadása kötelező.")
    private String contactPerson;

    @Email(message = "Érvénytelen email formátum.")
    private String email;

    private String phone;
    private String country;
    private String city;
    private String postalCode;
}
