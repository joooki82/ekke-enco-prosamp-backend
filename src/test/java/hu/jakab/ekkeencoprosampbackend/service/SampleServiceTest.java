package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sample.SampleResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.SampleMapper;
import hu.jakab.ekkeencoprosampbackend.model.Sample;
import hu.jakab.ekkeencoprosampbackend.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SampleServiceTest {

    @Mock
    private SampleRepository repository;

    @Mock
    private SampleMapper mapper;

    @Mock
    private SamplingRecordDatM200Repository samplingRecordRepository;

    @Mock
    private MeasurementUnitRepository measurementUnitRepository;

    @Mock
    private SamplingTypeRepository samplingTypeRepository;

    @Mock
    private AdjustmentMethodRepository adjustmentMethodRepository;

    @InjectMocks
    private SampleService sampleService;

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        var result = sampleService.getAll();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        Sample sample = new Sample();
        when(repository.findById(1L)).thenReturn(Optional.of(sample));
        when(mapper.toResponseDTO(sample)).thenReturn(new SampleResponseDTO());
        var result = sampleService.getById(1L);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> sampleService.getById(1L));
    }

    @Test
    void testSave() {
        SampleRequestDTO dto = new SampleRequestDTO();
        Sample sample = new Sample();
        when(mapper.toEntity(dto)).thenReturn(sample);
        when(repository.save(sample)).thenReturn(sample);
        when(mapper.toCreatedDTO(sample)).thenReturn(new SampleCreatedDTO());
        var result = sampleService.save(dto);
        assertNotNull(result);
        verify(repository, times(1)).save(sample);
    }

    @Test
    void testSaveDuplicate() {
        SampleRequestDTO dto = new SampleRequestDTO();
        Sample sample = new Sample();
        when(mapper.toEntity(dto)).thenReturn(sample);
        when(repository.save(sample)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(RuntimeException.class, () -> sampleService.save(dto));
    }

    @Test
    void testUpdate() {
        SampleRequestDTO dto = new SampleRequestDTO();
        Sample sample = new Sample();
        when(repository.findById(1L)).thenReturn(Optional.of(sample));
        when(repository.save(sample)).thenReturn(sample);
        when(mapper.toResponseDTO(sample)).thenReturn(new SampleResponseDTO());
        var result = sampleService.update(1L, dto);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(sample);
    }

    @Test
    void testUpdateNotFound() {
        SampleRequestDTO dto = new SampleRequestDTO();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> sampleService.update(1L, dto));
    }

    @Test
    void testDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        sampleService.delete(1L);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> sampleService.delete(1L));
    }
}
