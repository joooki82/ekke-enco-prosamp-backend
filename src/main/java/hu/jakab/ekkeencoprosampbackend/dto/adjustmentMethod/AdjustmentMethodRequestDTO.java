package hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod;

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
public class AdjustmentMethodRequestDTO {

    @NotNull(message = "A kód megadása kötelező.")
    @Size(max = 10, message = "A kód legfeljebb 10 karakter hosszú lehet.")
    private String code;

    @NotNull(message = "A leírás megadása kötelező.")
    private String description;
}
