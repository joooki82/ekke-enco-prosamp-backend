package hu.jakab.ekkeencoprosampbackend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyBasicInfoDTO {

    private Long id;
    private String name;
    private String contactPerson;
    private String email;
    private String phone;
    private String country;
    private String city;
}
