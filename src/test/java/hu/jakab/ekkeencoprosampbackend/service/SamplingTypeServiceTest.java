package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.samplingType.SamplingTypeCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingType.SamplingTypeRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingType.SamplingTypeResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.SamplingTypeMapper;
import hu.jakab.ekkeencoprosampbackend.model.SamplingType;
import hu.jakab.ekkeencoprosampbackend.repository.SamplingTypeRepository;
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
class SamplingTypeServiceTest {

    @Mock
    private SamplingTypeRepository repository;

    @Mock
    private SamplingTypeMapper mapper;

    @InjectMocks
    private SamplingTypeService samplingTypeService;

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        var result = samplingTypeService.getAll();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        SamplingType samplingType = new SamplingType();
        when(repository.findById(1L)).thenReturn(Optional.of(samplingType));
        when(mapper.toResponseDTO(samplingType)).thenReturn(new SamplingTypeResponseDTO());
        var result = samplingTypeService.getById(1L);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> samplingTypeService.getById(1L));
    }

    @Test
    void testSave() {
        SamplingTypeRequestDTO dto = new SamplingTypeRequestDTO();
        SamplingType samplingType = new SamplingType();
        when(mapper.toEntity(dto)).thenReturn(samplingType);
        when(repository.save(samplingType)).thenReturn(samplingType);
        when(mapper.toCreatedDTO(samplingType)).thenReturn(new SamplingTypeCreatedDTO());
        var result = samplingTypeService.save(dto);
        assertNotNull(result);
        verify(repository, times(1)).save(samplingType);
    }

    @Test
    void testSaveDuplicate() {
        SamplingTypeRequestDTO dto = new SamplingTypeRequestDTO();
        SamplingType samplingType = new SamplingType();
        when(mapper.toEntity(dto)).thenReturn(samplingType);
        when(repository.save(samplingType)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> samplingTypeService.save(dto));
    }

    @Test
    void testUpdate() {
        SamplingTypeRequestDTO dto = new SamplingTypeRequestDTO();
        SamplingType samplingType = new SamplingType();
        when(repository.findById(1L)).thenReturn(Optional.of(samplingType));
        when(repository.save(samplingType)).thenReturn(samplingType);
        when(mapper.toResponseDTO(samplingType)).thenReturn(new SamplingTypeResponseDTO());
        var result = samplingTypeService.update(1L, dto);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(samplingType);
    }

    @Test
    void testUpdateNotFound() {
        SamplingTypeRequestDTO dto = new SamplingTypeRequestDTO();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> samplingTypeService.update(1L, dto));
    }

    @Test
    void testDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        samplingTypeService.delete(1L);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> samplingTypeService.delete(1L));
    }
}
