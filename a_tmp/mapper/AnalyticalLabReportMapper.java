package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.AnalyticalLabReportRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.AnalyticalLabReportResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.AnalyticalLabReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AnalyticalLabReportMapper {
    AnalyticalLabReportMapper INSTANCE = Mappers.getMapper(AnalyticalLabReportMapper.class);

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "laboratory", ignore = true) // Handled manually in Service
    AnalyticalLabReport toEntity(AnalyticalLabReportRequestDTO dto);

    @Mapping(source = "laboratory.id", target = "laboratoryId")
    AnalyticalLabReportResponseDTO toResponseDTO(AnalyticalLabReport entity);
}
