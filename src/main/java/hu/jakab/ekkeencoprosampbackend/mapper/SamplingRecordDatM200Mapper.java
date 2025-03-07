package hu.jakab.ekkeencoprosampbackend.mapper;


import hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200.SamplingRecordDatM200CreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200.SamplingRecordDatM200RequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200.SamplingRecordDatM200ResponseDTO;
import hu.jakab.ekkeencoprosampbackend.model.SamplingRecordDatM200;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {EntityMapperHelper.class})
public interface SamplingRecordDatM200Mapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "conductedBy", source = "conductedById", qualifiedByName = "mapUser")
    @Mapping(target = "company", source = "companyId", qualifiedByName = "mapCompany")
    @Mapping(target = "siteLocation", source = "siteLocationId", qualifiedByName = "mapLocation")
    @Mapping(target = "project", source = "projectId", qualifiedByName = "mapProject")
    @Mapping(target = "samplingRecordEquipments", source = "equipmentIds", qualifiedByName = "mapEquipments")
    SamplingRecordDatM200 toEntity(SamplingRecordDatM200RequestDTO dto);

    @Mapping(source = "samplingRecordEquipments", target = "samplingRecordEquipments", qualifiedByName = "mapEquipmentList")
    SamplingRecordDatM200ResponseDTO toResponseDTO(SamplingRecordDatM200 entity);

    @Mapping(source = "samplingRecordEquipments", target = "samplingRecordEquipments", qualifiedByName = "mapEquipmentList")
    SamplingRecordDatM200CreatedDTO toCreatedDTO(SamplingRecordDatM200 entity);
}
