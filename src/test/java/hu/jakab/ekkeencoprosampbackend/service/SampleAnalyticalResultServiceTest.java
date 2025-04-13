package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.sampleAnalyticalResult.SampleAnalyticalResultCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sampleAnalyticalResult.SampleAnalyticalResultRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.sampleAnalyticalResult.SampleAnalyticalResultResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.SampleAnalyticalResultMapper;
import hu.jakab.ekkeencoprosampbackend.model.*;
import hu.jakab.ekkeencoprosampbackend.repository.*;
import hu.jakab.ekkeencoprosampbackend.service.utils.CalculationEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SampleAnalyticalResultServiceTest {

    @Mock
    private SampleAnalyticalResultRepository repository;

    @Mock
    private SampleRepository sampleRepository;

    @Mock
    private AnalyticalLabReportRepository analyticalLabReportRepository;

    @Mock
    private MeasurementUnitRepository measurementUnitRepository;

    @Mock
    private SampleContaminantRepository sampleContaminantRepository;

    @Mock
    private SampleAnalyticalResultMapper mapper;

    @Mock
    private CalculationEngine calculationEngine;

    @InjectMocks
    private SampleAnalyticalResultService service;

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        var result = service.getAll();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        SampleAnalyticalResult result = new SampleAnalyticalResult();
        when(repository.findById(1L)).thenReturn(Optional.of(result));
        when(mapper.toResponseDTO(result)).thenReturn(new SampleAnalyticalResultResponseDTO());
        var response = service.getById(1L);
        assertNotNull(response);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getById(1L));
    }

    @Test
    void testSave() {
        SampleAnalyticalResultRequestDTO dto = new SampleAnalyticalResultRequestDTO();
        SampleAnalyticalResult result = new SampleAnalyticalResult();
        Sample sample = new Sample();
        when(mapper.toEntity(dto)).thenReturn(result);
        when(sampleRepository.findById(anyLong())).thenReturn(Optional.of(sample));
        when(calculationEngine.calculateAdjustedTotalSampledVolume(sample)).thenReturn(BigDecimal.ONE);
        when(repository.save(result)).thenReturn(result);
        when(mapper.toCreatedDTO(result)).thenReturn(new SampleAnalyticalResultCreatedDTO());
        var response = service.save(dto);
        assertNotNull(response);
        verify(repository, times(1)).save(result);
    }

    @Test
    void testSaveDuplicate() {
        SampleAnalyticalResultRequestDTO dto = new SampleAnalyticalResultRequestDTO();
        SampleAnalyticalResult result = new SampleAnalyticalResult();
        when(mapper.toEntity(dto)).thenReturn(result);
        when(repository.save(result)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> service.save(dto));
    }

    @Test
    void testUpdate() {
        SampleAnalyticalResultRequestDTO dto = new SampleAnalyticalResultRequestDTO();
        SampleAnalyticalResult existing = new SampleAnalyticalResult();
        Sample sample = new Sample();
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(sampleRepository.findById(anyLong())).thenReturn(Optional.of(sample));
        when(calculationEngine.calculateAdjustedTotalSampledVolume(sample)).thenReturn(BigDecimal.ONE);
        when(repository.save(existing)).thenReturn(existing);
        when(mapper.toResponseDTO(existing)).thenReturn(new SampleAnalyticalResultResponseDTO());
        var response = service.update(1L, dto);
        assertNotNull(response);
        verify(repository, times(1)).save(existing);
    }

    @Test
    void testUpdateNotFound() {
        SampleAnalyticalResultRequestDTO dto = new SampleAnalyticalResultRequestDTO();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.update(1L, dto));
    }

    @Test
    void testDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        service.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
    }
}
