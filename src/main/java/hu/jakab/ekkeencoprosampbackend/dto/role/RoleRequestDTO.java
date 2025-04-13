package hu.jakab.ekkeencoprosampbackend.dto.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleRequestDTO {

    @NotBlank(message = "A szerepkör neve nem lehet üres.")
    private String roleName;
}
