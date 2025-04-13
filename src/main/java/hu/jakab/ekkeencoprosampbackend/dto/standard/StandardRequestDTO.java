package hu.jakab.ekkeencoprosampbackend.dto.standard;

import hu.jakab.ekkeencoprosampbackend.model.StandardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandardRequestDTO {

    @NotBlank(message = "A szabvány száma nem lehet üres.")
    @Size(max = 255, message = "A szabvány száma legfeljebb 255 karakter lehet.")
    private String standardNumber;

    private String description;

    @NotNull(message = "A szabvány típusa megadása kötelező.")
    private StandardType standardType;

    @NotBlank(message = "Az azonosító megadása kötelező.")
    @Size(max = 255, message = "Az azonosító legfeljebb 255 karakter lehet.")
    private String identifier;
}
