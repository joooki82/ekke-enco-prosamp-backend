package hu.jakab.ekkeencoprosampbackend.service;

import hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200.SamplingRecordDatM200CreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200.SamplingRecordDatM200RequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200.SamplingRecordDatM200ResponseDTO;
import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
import hu.jakab.ekkeencoprosampbackend.mapper.SamplingRecordDatM200Mapper;
import hu.jakab.ekkeencoprosampbackend.model.*;
import hu.jakab.ekkeencoprosampbackend.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SamplingRecordDatM200ServiceTest {

    @Mock
    private SamplingRecordDatM200Repository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EquipmentRepository equipmentRepository;

    @Mock
    private SamplingRecordEquipmentRepository samplingRecordEquipmentRepository;

    @Mock
    private SamplingRecordDatM200Mapper mapper;

    @InjectMocks
    private SamplingRecordDatM200Service samplingRecordDatM200Service;

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        var result = samplingRecordDatM200Service.getAll();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        SamplingRecordDatM200 record = new SamplingRecordDatM200();
        when(repository.findById(1L)).thenReturn(Optional.of(record));
        when(mapper.toResponseDTO(record)).thenReturn(new SamplingRecordDatM200ResponseDTO());
        var result = samplingRecordDatM200Service.getById(1L);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> samplingRecordDatM200Service.getById(1L));
    }

    @Test
    void testSave() {
        SamplingRecordDatM200RequestDTO dto = new SamplingRecordDatM200RequestDTO();
        SamplingRecordDatM200 record = new SamplingRecordDatM200();
        when(mapper.toEntity(dto)).thenReturn(record);
        when(repository.save(record)).thenReturn(record);
        when(mapper.toCreatedDTO(record)).thenReturn(new SamplingRecordDatM200CreatedDTO());
        var result = samplingRecordDatM200Service.save(dto);
        assertNotNull(result);
        verify(repository, times(1)).save(record);
    }

    @Test
    void testSaveDuplicate() {
        SamplingRecordDatM200RequestDTO dto = new SamplingRecordDatM200RequestDTO();
        SamplingRecordDatM200 record = new SamplingRecordDatM200();
        when(mapper.toEntity(dto)).thenReturn(record);
        when(repository.save(record)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DuplicateResourceException.class, () -> samplingRecordDatM200Service.save(dto));
    }

    @Test
    void testUpdate() {
        SamplingRecordDatM200RequestDTO dto = new SamplingRecordDatM200RequestDTO();
        SamplingRecordDatM200 record = new SamplingRecordDatM200();
        when(repository.findById(1L)).thenReturn(Optional.of(record));
        when(repository.save(record)).thenReturn(record);
        when(mapper.toResponseDTO(record)).thenReturn(new SamplingRecordDatM200ResponseDTO());
        var result = samplingRecordDatM200Service.update(1L, dto);
        assertNotNull(result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(record);
    }

    @Test
    void testUpdateNotFound() {
        SamplingRecordDatM200RequestDTO dto = new SamplingRecordDatM200RequestDTO();
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> samplingRecordDatM200Service.update(1L, dto));
    }

    @Test
    void testDelete() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        samplingRecordDatM200Service.delete(1L);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> samplingRecordDatM200Service.delete(1L));
    }
}
