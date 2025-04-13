package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.project.ProjectResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TestReportMapper.class})
public interface ProjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Project toEntity(ProjectRequestDTO dto);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientName", source = "client.name")
    @Mapping(target = "testReports", source = "testReports")
    ProjectResponseDTO toResponseDTO(Project entity);

    ProjectCreatedDTO toCreatedDTO(Project entity);

    ProjectListItemDTO toListItemDTO(Project entity);
}
