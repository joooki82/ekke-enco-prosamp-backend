package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.ProjectRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.ProjectResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "client", ignore = true) // Handled manually in Service
    Project toEntity(ProjectRequestDTO dto);

    @Mapping(source = "client.id", target = "clientId")
    ProjectResponseDTO toResponseDTO(Project entity);
}
