package hu.jakab.ekkeencoprosampbackend.dto.contaminantGroup;

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
public class ContaminantGroupRequestDTO {

    @NotBlank(message = "A csoport neve nem lehet üres.")
    @Size(max = 255, message = "A név legfeljebb 255 karakter lehet.")    private String name;

    @NotBlank(message = "A leírás megadása kötelező.")
    @Size(max = 255, message = "A leírás legfeljebb 255 karakter lehet.")
    private String description;
}
