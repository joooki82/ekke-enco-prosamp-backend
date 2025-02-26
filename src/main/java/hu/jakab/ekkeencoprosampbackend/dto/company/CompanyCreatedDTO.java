package hu.jakab.ekkeencoprosampbackend.dto.company;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyCreatedDTO {

    private Long id;
    private String name;
    private String contactPerson;
    private String email;
    private String phone;
    private String country;
    private String city;
}
