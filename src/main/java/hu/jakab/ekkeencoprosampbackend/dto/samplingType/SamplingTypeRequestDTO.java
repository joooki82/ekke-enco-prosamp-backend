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

    @NotBlank(message = "Code cannot be blank")
    @Size(max = 10, message = "Code must be at most 10 characters")
    private String code;

    @NotBlank(message = "Description cannot be blank")
    private String description;
}
