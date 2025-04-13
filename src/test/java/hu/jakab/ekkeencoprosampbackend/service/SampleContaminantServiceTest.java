package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.sampleContaminant.*;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.SampleContaminantMapper;
import hu.jakab.ekkeencoprosampbackend.model.*;
import hu.jakab.ekkeencoprosampbackend.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SampleContaminantServiceTest {

    @Mock
    private SampleRepository sampleRepository;

    @Mock
    private ContaminantRepository contaminantRepository;

    @Mock
    private SampleContaminantRepository sampleContaminantRepository;

    @Mock
    private SampleContaminantMapper mapper;

    @InjectMocks
    private SampleContaminantService sampleContaminantService;

    @Test
    void testLinkSampleToContaminant() {
        SampleContaminantRequestDTO requestDTO = new SampleContaminantRequestDTO(1L, 2L);
        Sample sample = new Sample();
        Contaminant contaminant = new Contaminant();
        SampleContaminant sampleContaminant = new SampleContaminant();

        when(sampleRepository.findById(1L)).thenReturn(Optional.of(sample));
        when(contaminantRepository.findById(2L)).thenReturn(Optional.of(contaminant));
        when(sampleContaminantRepository.findBySampleAndContaminant(sample, contaminant)).thenReturn(Optional.empty());
        when(mapper.toEntity(requestDTO)).thenReturn(sampleContaminant);
        when(sampleContaminantRepository.save(sampleContaminant)).thenReturn(sampleContaminant);
        when(mapper.toCreatedDTO(sampleContaminant)).thenReturn(new SampleContaminantCreatedDTO());

        var result = sampleContaminantService.linkSampleToContaminant(requestDTO);

        assertNotNull(result);
        verify(sampleRepository, times(1)).findById(1L);
        verify(contaminantRepository, times(1)).findById(2L);
        verify(sampleContaminantRepository, times(1)).save(sampleContaminant);
    }

    @Test
    void testUnlinkSampleFromContaminant() {
        SampleContaminantRequestDTO requestDTO = new SampleContaminantRequestDTO(1L, 2L);
        Sample sample = new Sample();
        Contaminant contaminant = new Contaminant();
        SampleContaminant sampleContaminant = new SampleContaminant();

        when(sampleRepository.findById(1L)).thenReturn(Optional.of(sample));
        when(contaminantRepository.findById(2L)).thenReturn(Optional.of(contaminant));
        when(sampleContaminantRepository.findBySampleAndContaminant(sample, contaminant)).thenReturn(Optional.of(sampleContaminant));

        sampleContaminantService.unlinkSampleFromContaminant(requestDTO);

        verify(sampleContaminantRepository, times(1)).delete(sampleContaminant);
    }

@Test
void testGetContaminantsBySample() {
    Long sampleId = 1L;
    Sample sample = Sample.builder().id(sampleId).build();
    SampleContaminant sampleContaminant = new SampleContaminant();
    List<SampleContaminant> sampleContaminants = List.of(sampleContaminant);

    when(sampleContaminantRepository.findBySample(any(Sample.class))).thenReturn(sampleContaminants);
    when(mapper.mapToSampleWithContaminantsDTO(sampleContaminants)).thenReturn(new SampleWithContaminantsDTO());

    var result = sampleContaminantService.getContaminantsBySample(sampleId);

    assertNotNull(result);
    verify(sampleContaminantRepository, times(1)).findBySample(any(Sample.class));
}
@Test
void testGetSampleContaminantsBySample() {
    Long sampleId = 1L;
    Sample sample = Sample.builder().id(sampleId).build();
    SampleContaminant sampleContaminant = new SampleContaminant();
    List<SampleContaminant> sampleContaminants = List.of(sampleContaminant);

    when(sampleContaminantRepository.findBySample(any(Sample.class))).thenReturn(sampleContaminants);
    when(mapper.mapToSampleWithSampleContaminantsDTO(sampleContaminants)).thenReturn(new SampleWithSampleContaminantsDTO());

    var result = sampleContaminantService.getSampleContaminantsBySample(sampleId);

    assertNotNull(result);
    verify(sampleContaminantRepository, times(1)).findBySample(any(Sample.class));
}
}
