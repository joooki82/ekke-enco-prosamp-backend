package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequestDTO {
    @NotBlank(message = "Project name cannot be empty")
    private String name;

    private String description;

    @NotNull(message = "Client ID is required")
    private Long clientId;
}
