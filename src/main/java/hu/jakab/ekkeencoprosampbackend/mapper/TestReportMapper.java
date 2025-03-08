package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.TestReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestReportMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    TestReport toEntity(TestReportRequestDTO dto);

    @Mapping(target = "project", source = "project")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "samplingRecord", source = "samplingRecord")
    TestReportResponseDTO toResponseDTO(TestReport entity);

    TestReportCreatedDTO toCreatedDTO(TestReport entity);

    @Mapping(target = "location", source = "location")
    TestReportListItemDTO toListItemDTO(TestReport entity);

}
