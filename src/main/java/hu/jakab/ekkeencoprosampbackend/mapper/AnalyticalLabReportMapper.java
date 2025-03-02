package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport.AnalyticalLabReportCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport.AnalyticalLabReportRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.analyticalLabReport.AnalyticalLabReportResponseDTO;
import hu.jakab.ekkeencoprosampbackend.dto.client.ClientCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.client.ClientRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.client.ClientResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.AnalyticalLabReport;
import hu.jakab.ekkeencoprosampbackend.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { EntityMapperHelper.class, LaboratoryMapper.class})
public interface AnalyticalLabReportMapper {

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "laboratory", source = "laboratoryId", qualifiedByName = "mapLaboratory")
    AnalyticalLabReport toEntity(AnalyticalLabReportRequestDTO dto);

    @Mapping(target = "laboratory", source = "laboratory")
    AnalyticalLabReportResponseDTO toResponseDTO(AnalyticalLabReport entity);

    AnalyticalLabReportCreatedDTO toCreatedDTO(AnalyticalLabReport entity);
}
