package hu.jakab.ekkeencoprosampbackend.dto.samplingType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SamplingTypeRequestDTO {

    @NotBlank(message = "A kód megadása kötelező.")
    @Size(max = 10, message = "A kód legfeljebb 10 karakter lehet.")
    private String code;

    @NotBlank(message = "A leírás megadása kötelező.")
    @Size(max = 255, message = "A leírás legfeljebb 255 karakter lehet.")
    private String description;
}
