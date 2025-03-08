package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportListItemDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.testreport.TestReportResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.TestReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {EntityMapperHelper.class, UserMapper.class, StandardMapper.class})
public interface TestReportMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "approvedBy", source = "approvedBy", qualifiedByName = "mapUser")
    @Mapping(target = "preparedBy", source = "preparedBy", qualifiedByName = "mapUser")
    @Mapping(target = "checkedBy", source = "checkedBy", qualifiedByName = "mapUser")
    TestReport toEntity(TestReportRequestDTO dto);

    @Mapping(target = "project", source = "project")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "samplingRecord", source = "samplingRecord")
    @Mapping(target = "approvedBy", source = "approvedBy")
    @Mapping(target = "preparedBy", source = "preparedBy")
    @Mapping(target = "checkedBy", source = "checkedBy")
    @Mapping(target = "testReportSamplers", source = "testReportSamplers", qualifiedByName = "mapSamplers")
    @Mapping(source = "testReportStandards", target = "testReportStandards", qualifiedByName = "mapStandards")
    TestReportResponseDTO toResponseDTO(TestReport entity);

    @Mapping(target = "project", source = "project")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "samplingRecord", source = "samplingRecord")
    @Mapping(target = "approvedBy", source = "approvedBy")
    @Mapping(target = "preparedBy", source = "preparedBy")
    TestReportCreatedDTO toCreatedDTO(TestReport entity);

    @Mapping(target = "location", source = "location")
    TestReportListItemDTO toListItemDTO(TestReport entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "approvedBy", source = "approvedBy", qualifiedByName = "mapUser")
    @Mapping(target = "preparedBy", source = "preparedBy", qualifiedByName = "mapUser")
    @Mapping(target = "checkedBy", source = "checkedBy", qualifiedByName = "mapUser")
    void updateEntityFromDTO(TestReportRequestDTO dto, @MappingTarget TestReport entity);

}
