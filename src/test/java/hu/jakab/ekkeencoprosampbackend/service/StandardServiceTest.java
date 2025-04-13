package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.standard.StandardCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.standard.StandardRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.standard.StandardResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.StandardMapper;
import hu.jakab.ekkeencoprosampbackend.model.Standard;
import hu.jakab.ekkeencoprosampbackend.repository.StandardRepository;
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
class StandardServiceTest {

    @Mock
    private StandardRepository repository;

    @Mock
    private StandardMapper mapper;

    @InjectMocks
    private StandardService standardService;

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        var result = standardService.getAll();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        Standard standard = new Standard();
        when(repository.findById(1L)).thenReturn(Optional.of(standard));
        when(mapper.toResponseDTO(standard)).thenReturn(new StandardResponseDTO());
        var result = standardService.getById(1L);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> standardService.getById(1L));
    }

    @Test
    void testSave() {
        StandardRequestDTO dto = new StandardRequestDTO();
        Standard standard = new Standard();
        when(mapper.toEntity(dto)).thenReturn(standard);
        when(repository.save(standard)).thenReturn(standard);
        when(mapper.toCreatedDTO(standard)).thenReturn(new StandardCreatedDTO());
        var result = standardService.save(dto);
        assertNotNull(result);
        verify(repository, times(1)).save(standard);
    }

    @Test
    void testSaveDuplicate() {
        StandardRequestDTO dto = new StandardRequestDTO();
        Standard standard = new Standard();
        when(mapper.toEntity(dto)).thenReturn(standard);
        when(repository.save(standard)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> standardService.save(dto));
    }

    @Test
    void testUpdate() {
        StandardRequestDTO dto = new StandardRequestDTO();
        Standard standard = new Standard();
        when(repository.findById(1L)).thenReturn(Optional.of(standard));
        when(repository.save(standard)).thenReturn(standard);
        when(mapper.toResponseDTO(standard)).thenReturn(new StandardResponseDTO());
        var result = standardService.update(1L, dto);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(standard);
    }

    @Test
    void testUpdateNotFound() {
        StandardRequestDTO dto = new StandardRequestDTO();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> standardService.update(1L, dto));
    }

    @Test
    void testUpdateDuplicate() {
        StandardRequestDTO dto = new StandardRequestDTO();
        Standard standard = new Standard();
        when(repository.findById(1L)).thenReturn(Optional.of(standard));
        when(repository.save(standard)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> standardService.update(1L, dto));
    }

    @Test
    void testDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        standardService.delete(1L);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> standardService.delete(1L));
    }
}
