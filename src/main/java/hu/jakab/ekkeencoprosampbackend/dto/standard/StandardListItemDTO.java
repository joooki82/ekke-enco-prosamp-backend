package hu.jakab.ekkeencoprosampbackend.dto.standard;

import lombok.*;

import java.time.LocalDateTime;

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
