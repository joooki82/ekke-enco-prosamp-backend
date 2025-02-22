package hu.jakab.ekkeencoprosampbackend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationRequestDTO {
    private Long companyId;

    @NotBlank(message = "Location name cannot be empty")
    private String name;

    private String address;

    @NotBlank(message = "Contact person cannot be empty")
