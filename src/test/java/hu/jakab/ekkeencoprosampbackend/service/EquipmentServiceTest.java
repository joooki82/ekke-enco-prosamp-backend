package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.equipment.EquipmentCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.equipment.EquipmentRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.equipment.EquipmentResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.EquipmentMapper;
import hu.jakab.ekkeencoprosampbackend.model.Equipment;
import hu.jakab.ekkeencoprosampbackend.repository.EquipmentRepository;
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
class EquipmentServiceTest {

    @Mock
    private EquipmentRepository repository;

    @Mock
    private EquipmentMapper mapper;

    @InjectMocks
    private EquipmentService equipmentService;

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        var result = equipmentService.getAll();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        Equipment equipment = new Equipment();
        when(repository.findById(1L)).thenReturn(Optional.of(equipment));
        when(mapper.toResponseDTO(equipment)).thenReturn(new EquipmentResponseDTO());
        var result = equipmentService.getById(1L);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> equipmentService.getById(1L));
    }

    @Test
    void testSave() {
        EquipmentRequestDTO dto = new EquipmentRequestDTO();
        Equipment equipment = new Equipment();
        when(mapper.toEntity(dto)).thenReturn(equipment);
        when(repository.save(equipment)).thenReturn(equipment);
        when(mapper.toCreatedDTO(equipment)).thenReturn(new EquipmentCreatedDTO());
        var result = equipmentService.save(dto);
        assertNotNull(result);
        verify(repository, times(1)).save(equipment);
    }

    @Test
    void testSaveDuplicate() {
        EquipmentRequestDTO dto = new EquipmentRequestDTO();
        Equipment equipment = new Equipment();
        when(mapper.toEntity(dto)).thenReturn(equipment);
        when(repository.save(equipment)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> equipmentService.save(dto));
    }

    @Test
    void testUpdate() {
        EquipmentRequestDTO dto = new EquipmentRequestDTO();
        Equipment equipment = new Equipment();
        when(repository.findById(1L)).thenReturn(Optional.of(equipment));
        when(repository.save(equipment)).thenReturn(equipment);
        when(mapper.toResponseDTO(equipment)).thenReturn(new EquipmentResponseDTO());
        var result = equipmentService.update(1L, dto);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(equipment);
    }

    @Test
    void testUpdateNotFound() {
        EquipmentRequestDTO dto = new EquipmentRequestDTO();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> equipmentService.update(1L, dto));
    }

    @Test
    void testUpdateDuplicate() {
        EquipmentRequestDTO dto = new EquipmentRequestDTO();
        Equipment equipment = new Equipment();
        when(repository.findById(1L)).thenReturn(Optional.of(equipment));
        when(repository.save(equipment)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> equipmentService.update(1L, dto));
    }

    @Test
    void testDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        equipmentService.delete(1L);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> equipmentService.delete(1L));
    }
}
