package hu.jakab.ekkeencoprosampbackend.dto.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationListItemDTO {
    private Long id;
    private String name;
    private String city;
}
