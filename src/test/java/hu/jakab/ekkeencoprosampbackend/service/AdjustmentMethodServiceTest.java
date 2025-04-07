package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.adjustmentMethod.AdjustmentMethodResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.AdjustmentMethodMapper;
import hu.jakab.ekkeencoprosampbackend.model.AdjustmentMethod;
import hu.jakab.ekkeencoprosampbackend.repository.AdjustmentMethodRepository;
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
class AdjustmentMethodServiceTest {

    @Mock
    private AdjustmentMethodRepository repository;

    @Mock
    private AdjustmentMethodMapper mapper;

    @InjectMocks
    private AdjustmentMethodService adjustmentMethodService;

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        var result = adjustmentMethodService.getAll();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        AdjustmentMethod adjustmentMethod = new AdjustmentMethod();
        when(repository.findById(1L)).thenReturn(Optional.of(adjustmentMethod));
        when(mapper.toResponseDTO(adjustmentMethod)).thenReturn(new AdjustmentMethodResponseDTO());
        var result = adjustmentMethodService.getById(1L);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> adjustmentMethodService.getById(1L));
    }

    @Test
    void testSave() {
        AdjustmentMethodRequestDTO dto = new AdjustmentMethodRequestDTO();
        AdjustmentMethod adjustmentMethod = new AdjustmentMethod();
        when(mapper.toEntity(dto)).thenReturn(adjustmentMethod);
        when(repository.save(adjustmentMethod)).thenReturn(adjustmentMethod);
        when(mapper.toCreatedDTO(adjustmentMethod)).thenReturn(new AdjustmentMethodCreatedDTO());
        var result = adjustmentMethodService.save(dto);
        assertNotNull(result);
        verify(repository, times(1)).save(adjustmentMethod);
    }

    @Test
    void testSaveDuplicate() {
        AdjustmentMethodRequestDTO dto = new AdjustmentMethodRequestDTO();
        AdjustmentMethod adjustmentMethod = new AdjustmentMethod();
        when(mapper.toEntity(dto)).thenReturn(adjustmentMethod);
        when(repository.save(adjustmentMethod)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> adjustmentMethodService.save(dto));
    }

    @Test
    void testUpdate() {
        AdjustmentMethodRequestDTO dto = new AdjustmentMethodRequestDTO();
        AdjustmentMethod adjustmentMethod = new AdjustmentMethod();
        when(repository.findById(1L)).thenReturn(Optional.of(adjustmentMethod));
        when(repository.save(adjustmentMethod)).thenReturn(adjustmentMethod);
        when(mapper.toResponseDTO(adjustmentMethod)).thenReturn(new AdjustmentMethodResponseDTO());
        var result = adjustmentMethodService.update(1L, dto);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(adjustmentMethod);
    }

    @Test
    void testUpdateNotFound() {
        AdjustmentMethodRequestDTO dto = new AdjustmentMethodRequestDTO();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> adjustmentMethodService.update(1L, dto));
    }

    @Test
    void testUpdateDuplicate() {
        AdjustmentMethodRequestDTO dto = new AdjustmentMethodRequestDTO();
        AdjustmentMethod adjustmentMethod = new AdjustmentMethod();
        when(repository.findById(1L)).thenReturn(Optional.of(adjustmentMethod));
        when(repository.save(adjustmentMethod)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> adjustmentMethodService.update(1L, dto));
    }

    @Test
    void testDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        adjustmentMethodService.delete(1L);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> adjustmentMethodService.delete(1L));
    }
}
