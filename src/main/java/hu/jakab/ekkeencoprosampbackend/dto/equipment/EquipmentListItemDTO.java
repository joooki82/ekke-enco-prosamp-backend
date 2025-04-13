package hu.jakab.ekkeencoprosampbackend.dto.equipment;

import lombok.Data;

@Data
public class EquipmentListItemDTO {
    private Long id;
    private String name;
    private String identifier;
    private String manufacturer;
    private String type;
}
