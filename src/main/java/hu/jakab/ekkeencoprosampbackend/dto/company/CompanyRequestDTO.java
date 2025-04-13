package hu.jakab.ekkeencoprosampbackend.dto.company;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRequestDTO {

    @NotBlank(message = "A cég név megadása kötelező.")
    private String name;

    private String address;

    @NotBlank(message = "A kapcsolattartó megadása kötelező.")
    private String contactPerson;

    @Email(message = "Érvénytelen email formátum.")
    private String email;

    private String phone;
    private String country;
    private String city;
}
