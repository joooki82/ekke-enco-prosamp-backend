package hu.jakab.ekkeencoprosampbackend.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequestDTO {

    @NotBlank(message = "Az ügyfél neve kötelező.")
    private String name;

    @NotBlank(message = "A kapcsolattartó megadása kötelező.")
    private String contactPerson;

    @Email(message = "Érvénytelen email formátum.")
    private String email;

    private String phone;
    private String address;

    private String country;

    private String city;

    private String postalCode;

    private String taxNumber;
}
