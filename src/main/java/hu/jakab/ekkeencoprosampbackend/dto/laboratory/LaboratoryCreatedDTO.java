package hu.jakab.ekkeencoprosampbackend.dto.laboratory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaboratoryCreatedDTO {
    private Long id;
    private String name;
    private String accreditation;
    private String contactEmail;
    private String phone;
    private String address;
    private String website;
}
