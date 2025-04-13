package hu.jakab.ekkeencoprosampbackend.dto.laboratory;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaboratoryRequestDTO {

    @NotBlank(message = "A laboratórium neve nem lehet üres.")
    @Size(max = 255, message = "A név legfeljebb 255 karakter lehet.")
    private String name;

    @Size(max = 255, message = "Az akkreditációs szám legfeljebb 255 karakter lehet.")
    private String accreditation;

    @Email(message = "Érvénytelen email formátum.")
    private String contactEmail;

    private String phone;
    private String address;
    private String website;
}
