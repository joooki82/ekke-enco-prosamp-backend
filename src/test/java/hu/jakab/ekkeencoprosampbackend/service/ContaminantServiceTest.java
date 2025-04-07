package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.contaminant.ContaminantResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.ContaminantMapper;
import hu.jakab.ekkeencoprosampbackend.model.Contaminant;
import hu.jakab.ekkeencoprosampbackend.repository.ContaminantRepository;
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
class ContaminantServiceTest {

    @Mock
    private ContaminantRepository repository;

    @Mock
    private ContaminantMapper mapper;

    @InjectMocks
    private ContaminantService contaminantService;

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        var result = contaminantService.getAll();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        Contaminant contaminant = new Contaminant();
        when(repository.findById(1L)).thenReturn(Optional.of(contaminant));
        when(mapper.toResponseDTO(contaminant)).thenReturn(new ContaminantResponseDTO());
        var result = contaminantService.getById(1L);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> contaminantService.getById(1L));
    }

    @Test
    void testSave() {
        ContaminantRequestDTO dto = new ContaminantRequestDTO();
        Contaminant contaminant = new Contaminant();
        when(mapper.toEntity(dto)).thenReturn(contaminant);
        when(repository.save(contaminant)).thenReturn(contaminant);
        when(mapper.toCreatedDTO(contaminant)).thenReturn(new ContaminantCreatedDTO());
        var result = contaminantService.save(dto);
        assertNotNull(result);
        verify(repository, times(1)).save(contaminant);
    }

    @Test
    void testSaveDuplicate() {
        ContaminantRequestDTO dto = new ContaminantRequestDTO();
        Contaminant contaminant = new Contaminant();
        when(mapper.toEntity(dto)).thenReturn(contaminant);
        when(repository.save(contaminant)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> contaminantService.save(dto));
    }

    @Test
    void testUpdate() {
        ContaminantRequestDTO dto = new ContaminantRequestDTO();
        Contaminant contaminant = new Contaminant();
        when(repository.findById(1L)).thenReturn(Optional.of(contaminant));
        when(repository.save(contaminant)).thenReturn(contaminant);
        when(mapper.toResponseDTO(contaminant)).thenReturn(new ContaminantResponseDTO());
        var result = contaminantService.update(1L, dto);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(contaminant);
    }

    @Test
    void testUpdateNotFound() {
        ContaminantRequestDTO dto = new ContaminantRequestDTO();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> contaminantService.update(1L, dto));
    }

    @Test
    void testUpdateDuplicate() {
        ContaminantRequestDTO dto = new ContaminantRequestDTO();
        Contaminant contaminant = new Contaminant();
        when(repository.findById(1L)).thenReturn(Optional.of(contaminant));
        when(repository.save(contaminant)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> contaminantService.update(1L, dto));
    }

    @Test
    void testDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        contaminantService.delete(1L);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> contaminantService.delete(1L));
    }
}
