package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.contaminantGroup.ContaminantGroupCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.contaminantGroup.ContaminantGroupRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.contaminantGroup.ContaminantGroupResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.ContaminantGroupMapper;
import hu.jakab.ekkeencoprosampbackend.model.ContaminantGroup;
import hu.jakab.ekkeencoprosampbackend.repository.ContaminantGroupRepository;
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
class ContaminantGroupServiceTest {

    @Mock
    private ContaminantGroupRepository repository;

    @Mock
    private ContaminantGroupMapper mapper;

    @InjectMocks
    private ContaminantGroupService contaminantGroupService;

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        var result = contaminantGroupService.getAll();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        ContaminantGroup contaminantGroup = new ContaminantGroup();
        when(repository.findById(1L)).thenReturn(Optional.of(contaminantGroup));
        when(mapper.toResponseDTO(contaminantGroup)).thenReturn(new ContaminantGroupResponseDTO());
        var result = contaminantGroupService.getById(1L);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> contaminantGroupService.getById(1L));
    }

    @Test
    void testSave() {
        ContaminantGroupRequestDTO dto = new ContaminantGroupRequestDTO();
        ContaminantGroup contaminantGroup = new ContaminantGroup();
        when(mapper.toEntity(dto)).thenReturn(contaminantGroup);
        when(repository.save(contaminantGroup)).thenReturn(contaminantGroup);
        when(mapper.toCreatedDTO(contaminantGroup)).thenReturn(new ContaminantGroupCreatedDTO());
        var result = contaminantGroupService.save(dto);
        assertNotNull(result);
        verify(repository, times(1)).save(contaminantGroup);
    }

    @Test
    void testSaveDuplicate() {
        ContaminantGroupRequestDTO dto = new ContaminantGroupRequestDTO();
        ContaminantGroup contaminantGroup = new ContaminantGroup();
        when(mapper.toEntity(dto)).thenReturn(contaminantGroup);
        when(repository.save(contaminantGroup)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> contaminantGroupService.save(dto));
    }

    @Test
    void testUpdate() {
        ContaminantGroupRequestDTO dto = new ContaminantGroupRequestDTO();
        ContaminantGroup contaminantGroup = new ContaminantGroup();
        when(repository.findById(1L)).thenReturn(Optional.of(contaminantGroup));
        when(repository.save(contaminantGroup)).thenReturn(contaminantGroup);
        when(mapper.toResponseDTO(contaminantGroup)).thenReturn(new ContaminantGroupResponseDTO());
        var result = contaminantGroupService.update(1L, dto);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(contaminantGroup);
    }

    @Test
    void testUpdateNotFound() {
        ContaminantGroupRequestDTO dto = new ContaminantGroupRequestDTO();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> contaminantGroupService.update(1L, dto));
    }

    @Test
    void testUpdateDuplicate() {
        ContaminantGroupRequestDTO dto = new ContaminantGroupRequestDTO();
        ContaminantGroup contaminantGroup = new ContaminantGroup();
        when(repository.findById(1L)).thenReturn(Optional.of(contaminantGroup));
        when(repository.save(contaminantGroup)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> contaminantGroupService.update(1L, dto));
    }

    @Test
    void testDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        contaminantGroupService.delete(1L);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> contaminantGroupService.delete(1L));
    }
}
