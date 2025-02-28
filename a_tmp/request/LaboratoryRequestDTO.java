package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaboratoryRequestDTO {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String accreditation;

    @Email(message = "Invalid email format")
    private String contactEmail;

    private String phone;
    private String address;
    private String website;
}
