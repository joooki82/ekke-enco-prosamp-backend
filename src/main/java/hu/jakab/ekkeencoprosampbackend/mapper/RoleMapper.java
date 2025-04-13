package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.role.RoleCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.role.RoleRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.role.RoleResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EntityMapperHelper.class})
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    Role toEntity(RoleRequestDTO dto);

    RoleResponseDTO toResponseDTO(Role entity);

    RoleCreatedDTO toCreatedDTO(Role entity);
}
