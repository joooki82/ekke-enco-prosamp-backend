package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.laboratory.LaboratoryCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.laboratory.LaboratoryRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.laboratory.LaboratoryResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.LaboratoryMapper;
import hu.jakab.ekkeencoprosampbackend.model.Laboratory;
import hu.jakab.ekkeencoprosampbackend.repository.LaboratoryRepository;
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
class LaboratoryServiceTest {

    @Mock
    private LaboratoryRepository repository;

    @Mock
    private LaboratoryMapper mapper;

    @InjectMocks
    private LaboratoryService laboratoryService;

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        var result = laboratoryService.getAll();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        Laboratory laboratory = new Laboratory();
        when(repository.findById(1L)).thenReturn(Optional.of(laboratory));
        when(mapper.toResponseDTO(laboratory)).thenReturn(new LaboratoryResponseDTO());
        var result = laboratoryService.getById(1L);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> laboratoryService.getById(1L));
    }

    @Test
    void testSave() {
        LaboratoryRequestDTO dto = new LaboratoryRequestDTO();
        Laboratory laboratory = new Laboratory();
        when(mapper.toEntity(dto)).thenReturn(laboratory);
        when(repository.save(laboratory)).thenReturn(laboratory);
        when(mapper.toCreatedDTO(laboratory)).thenReturn(new LaboratoryCreatedDTO());
        var result = laboratoryService.save(dto);
        assertNotNull(result);
        verify(repository, times(1)).save(laboratory);
    }

    @Test
    void testSaveDuplicate() {
        LaboratoryRequestDTO dto = new LaboratoryRequestDTO();
        Laboratory laboratory = new Laboratory();
        when(mapper.toEntity(dto)).thenReturn(laboratory);
        when(repository.save(laboratory)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> laboratoryService.save(dto));
    }

    @Test
    void testUpdate() {
        LaboratoryRequestDTO dto = new LaboratoryRequestDTO();
        Laboratory laboratory = new Laboratory();
        when(repository.findById(1L)).thenReturn(Optional.of(laboratory));
        when(repository.save(laboratory)).thenReturn(laboratory);
        when(mapper.toResponseDTO(laboratory)).thenReturn(new LaboratoryResponseDTO());
        var result = laboratoryService.update(1L, dto);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(laboratory);
    }

    @Test
    void testUpdateNotFound() {
        LaboratoryRequestDTO dto = new LaboratoryRequestDTO();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> laboratoryService.update(1L, dto));
    }

    @Test
    void testUpdateDuplicate() {
        LaboratoryRequestDTO dto = new LaboratoryRequestDTO();
        Laboratory laboratory = new Laboratory();
        when(repository.findById(1L)).thenReturn(Optional.of(laboratory));
        when(repository.save(laboratory)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> laboratoryService.update(1L, dto));
    }

    @Test
    void testDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        laboratoryService.delete(1L);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> laboratoryService.delete(1L));
    }
}
