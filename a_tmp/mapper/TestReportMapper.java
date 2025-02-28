package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.request.TestReportRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.TestReportResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.TestReport;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TestReportMapper {
    TestReportMapper INSTANCE = Mappers.getMapper(TestReportMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "project", ignore = true) // Handled manually in Service
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "samplingRecord", ignore = true)
    TestReport toEntity(TestReportRequestDTO dto);

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "samplingRecord.id", target = "samplingRecordId")
    TestReportResponseDTO toResponseDTO(TestReport entity);
}
