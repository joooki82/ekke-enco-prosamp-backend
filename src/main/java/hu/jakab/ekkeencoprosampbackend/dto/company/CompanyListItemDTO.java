package hu.jakab.ekkeencoprosampbackend.dto.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyListItemDTO {
    private Long id;
    private String name;
    private String address;
}
