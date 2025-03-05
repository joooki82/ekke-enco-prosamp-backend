package hu.jakab.ekkeencoprosampbackend.mapper;

import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantListNameDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleIdentifierDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sampleContaminant.*;
import hu.jakab.ekkeencoprosampbackend.model.Contaminant;
import hu.jakab.ekkeencoprosampbackend.model.Sample;
import hu.jakab.ekkeencoprosampbackend.model.SampleContaminant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", uses = {SampleMapper.class,
        ContaminantMapper.class, MeasurementUnitMapper.class,
        AnalyticalLabReportMapper.class,
        EntityMapperHelper.class})
public interface SampleContaminantMapper {

    @Mapping(target = "id", ignore = true) // ID is auto-generated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "sample", source = "sampleId", qualifiedByName = "mapSample")
    @Mapping(target = "contaminant", source = "contaminantId", qualifiedByName = "mapContaminant")
    SampleContaminant toEntity(SampleContaminantRequestDTO dto);

    @Mapping(target = "sample", source = "sample")
    @Mapping(target = "contaminant", source = "contaminant")
    SampleContaminantCreatedDTO toCreatedDTO(SampleContaminant entity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "sampleIdentifier", target = "sampleIdentifier")
    SampleIdentifierDTO sampleToSampleIdentifierDTO(Sample sample);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    ContaminantListNameDTO contaminantToContaminantListNameDTO(Contaminant contaminant);


    default SampleWithContaminantsDTO mapToSampleWithContaminantsDTO(List<SampleContaminant> sampleContaminants) {
        if (sampleContaminants == null || sampleContaminants.isEmpty()) {
            return null;
        }

        Sample sample = sampleContaminants.getFirst().getSample(); // All contaminants belong to the same sample
        SampleIdentifierDTO sampleDTO = sampleToSampleIdentifierDTO(sample);

        List<ContaminantListNameDTO> contaminantsDTO = sampleContaminants.stream()
                .map(sc -> contaminantToContaminantListNameDTO(sc.getContaminant()))
                .collect(Collectors.toList());

        return SampleWithContaminantsDTO.builder()
                .sample(sampleDTO)
                .contaminants(contaminantsDTO)
                .build();
    }


}
