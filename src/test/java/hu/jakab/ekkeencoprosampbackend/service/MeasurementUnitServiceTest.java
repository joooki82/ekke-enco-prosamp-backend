package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.measurementUnit.MeasurementUnitCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.measurementUnit.MeasurementUnitRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.measurementUnit.MeasurementUnitResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.MeasurementUnitMapper;
import hu.jakab.ekkeencoprosampbackend.model.MeasurementUnit;
import hu.jakab.ekkeencoprosampbackend.repository.MeasurementUnitRepository;
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
class MeasurementUnitServiceTest {

    @Mock
    private MeasurementUnitRepository repository;

    @Mock
    private MeasurementUnitMapper mapper;

    @InjectMocks
    private MeasurementUnitService measurementUnitService;

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        var result = measurementUnitService.getAll();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        MeasurementUnit measurementUnit = new MeasurementUnit();
        when(repository.findById(1L)).thenReturn(Optional.of(measurementUnit));
        when(mapper.toResponseDTO(measurementUnit)).thenReturn(new MeasurementUnitResponseDTO());
        var result = measurementUnitService.getById(1L);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> measurementUnitService.getById(1L));
    }

    @Test
    void testSave() {
        MeasurementUnitRequestDTO dto = new MeasurementUnitRequestDTO();
        MeasurementUnit measurementUnit = new MeasurementUnit();
        when(mapper.toEntity(dto)).thenReturn(measurementUnit);
        when(repository.save(measurementUnit)).thenReturn(measurementUnit);
        when(mapper.toCreatedDTO(measurementUnit)).thenReturn(new MeasurementUnitCreatedDTO());
        var result = measurementUnitService.save(dto);
        assertNotNull(result);
        verify(repository, times(1)).save(measurementUnit);
    }

    @Test
    void testSaveDuplicate() {
        MeasurementUnitRequestDTO dto = new MeasurementUnitRequestDTO();
        MeasurementUnit measurementUnit = new MeasurementUnit();
        when(mapper.toEntity(dto)).thenReturn(measurementUnit);
        when(repository.save(measurementUnit)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> measurementUnitService.save(dto));
    }

    @Test
    void testUpdate() {
        MeasurementUnitRequestDTO dto = new MeasurementUnitRequestDTO();
        MeasurementUnit measurementUnit = new MeasurementUnit();
        when(repository.findById(1L)).thenReturn(Optional.of(measurementUnit));
        when(repository.save(measurementUnit)).thenReturn(measurementUnit);
        when(mapper.toResponseDTO(measurementUnit)).thenReturn(new MeasurementUnitResponseDTO());
        var result = measurementUnitService.update(1L, dto);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(measurementUnit);
    }

    @Test
    void testUpdateNotFound() {
        MeasurementUnitRequestDTO dto = new MeasurementUnitRequestDTO();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> measurementUnitService.update(1L, dto));
    }

    @Test
    void testUpdateDuplicate() {
        MeasurementUnitRequestDTO dto = new MeasurementUnitRequestDTO();
        MeasurementUnit measurementUnit = new MeasurementUnit();
        when(repository.findById(1L)).thenReturn(Optional.of(measurementUnit));
        when(repository.save(measurementUnit)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> measurementUnitService.update(1L, dto));
    }

    @Test
    void testDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        measurementUnitService.delete(1L);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> measurementUnitService.delete(1L));
    }
}
