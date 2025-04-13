package hu.jakab.ekkeencoprosampbackend.dto.standard;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandardListItemDTO {

    private Long id;
    private String standardNumber;
    private String description;
}
