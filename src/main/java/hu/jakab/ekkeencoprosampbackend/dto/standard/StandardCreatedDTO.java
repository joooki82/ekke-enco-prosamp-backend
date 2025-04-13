package hu.jakab.ekkeencoprosampbackend.dto.standard;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandardCreatedDTO {

    private Long id;
    private String standardNumber;
    private String description;
    private String standardType;
    private String identifier;
}
