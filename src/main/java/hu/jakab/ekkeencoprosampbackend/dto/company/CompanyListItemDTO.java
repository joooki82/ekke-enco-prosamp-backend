package hu.jakab.ekkeencoprosampbackend.dto.company;

import hu.jakab.ekkeencoprosampbackend.dto.location.LocationListItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyListItemDTO {
    private Long id;
    private String name;
    private String address;
}
