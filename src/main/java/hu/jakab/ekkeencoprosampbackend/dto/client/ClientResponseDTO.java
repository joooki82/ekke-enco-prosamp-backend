package hu.jakab.ekkeencoprosampbackend.dto.client;

import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectListItemDTO;
import hu.jakab.ekkeencoprosampbackend.model.Project;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponseDTO {
    private Long id;
    private String name;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
    private String country;
    private String city;
    private String postalCode;
    private String taxNumber;
    private List<ProjectListItemDTO> projects = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
