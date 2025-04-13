package hu.jakab.ekkeencoprosampbackend.dto.contaminant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaminantRequestDTO {

    @NotBlank(message = "A szennyező anyag neve nem lehet üres.")
    private String name;

    @Size(max = 255, message = "A leírás legfeljebb 255 karakter lehet.")
    private String description;

    @NotNull(message = "A szennyező anyag csoport azonosítója kötelező.")
    private Long contaminantGroupId;

}
