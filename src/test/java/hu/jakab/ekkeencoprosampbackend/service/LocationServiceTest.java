package hu.jakab.ekkeencoprosampbackend.service;

    import hu.jakab.ekkeencoprosampbackend.dto.location.LocationCreatedDTO;
    import hu.jakab.ekkeencoprosampbackend.dto.location.LocationRequestDTO;
    import hu.jakab.ekkeencoprosampbackend.dto.location.LocationResponseDTO;
    import hu.jakab.ekkeencoprosampbackend.exception.DuplicateResourceException;
    import hu.jakab.ekkeencoprosampbackend.exception.ResourceNotFoundException;
    import hu.jakab.ekkeencoprosampbackend.mapper.LocationMapper;
    import hu.jakab.ekkeencoprosampbackend.model.Company;
    import hu.jakab.ekkeencoprosampbackend.model.Location;
    import hu.jakab.ekkeencoprosampbackend.repository.CompanyRepository;
    import hu.jakab.ekkeencoprosampbackend.repository.LocationRepository;
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
    class LocationServiceTest {

        @Mock
        private LocationRepository repository;

        @Mock
        private CompanyRepository companyRepository;

        @Mock
        private LocationMapper mapper;

        @InjectMocks
        private LocationService locationService;

        @Test
        void testGetAll() {
            when(repository.findAll()).thenReturn(Collections.emptyList());
            var result = locationService.getAll();
            assertTrue(result.isEmpty());
            verify(repository, times(1)).findAll();
        }

        @Test
        void testGetById() {
            Location location = new Location();
            when(repository.findById(1L)).thenReturn(Optional.of(location));
            when(mapper.toResponseDTO(location)).thenReturn(new LocationResponseDTO());
            var result = locationService.getById(1L);
            assertNotNull(result);
            verify(repository, times(1)).findById(1L);
        }

        @Test
        void testGetByIdNotFound() {
            when(repository.findById(1L)).thenReturn(Optional.empty());
            assertThrows(ResourceNotFoundException.class, () -> locationService.getById(1L));
        }

        @Test
        void testSave() {
            LocationRequestDTO dto = new LocationRequestDTO();
            Location location = new Location();
            when(mapper.toEntity(dto)).thenReturn(location);
            when(repository.save(location)).thenReturn(location);
            when(mapper.toCreatedDTO(location)).thenReturn(new LocationCreatedDTO());
            var result = locationService.save(dto);
            assertNotNull(result);
            verify(repository, times(1)).save(location);
        }

        @Test
        void testSaveDuplicate() {
            LocationRequestDTO dto = new LocationRequestDTO();
            Location location = new Location();
            when(mapper.toEntity(dto)).thenReturn(location);
            when(repository.save(location)).thenThrow(DataIntegrityViolationException.class);
            assertThrows(DuplicateResourceException.class, () -> locationService.save(dto));
        }

        @Test
        void testUpdate() {
            LocationRequestDTO dto = new LocationRequestDTO();
            Location location = new Location();
            Company company = new Company();
            when(repository.findById(1L)).thenReturn(Optional.of(location));
            when(companyRepository.findById(dto.getCompanyId())).thenReturn(Optional.of(company));
            when(repository.save(location)).thenReturn(location);
            when(mapper.toResponseDTO(location)).thenReturn(new LocationResponseDTO());
            var result = locationService.update(1L, dto);
            assertNotNull(result);
            verify(repository, times(1)).findById(1L);
            verify(repository, times(1)).save(location);
        }

        @Test
        void testUpdateNotFound() {
            LocationRequestDTO dto = new LocationRequestDTO();
            when(repository.findById(1L)).thenReturn(Optional.empty());
            assertThrows(ResourceNotFoundException.class, () -> locationService.update(1L, dto));
        }

        @Test
        void testUpdateDuplicate() {
            LocationRequestDTO dto = new LocationRequestDTO();
            Location location = new Location();
            when(repository.findById(1L)).thenReturn(Optional.of(location));
            when(repository.save(location)).thenThrow(DataIntegrityViolationException.class);
            assertThrows(DuplicateResourceException.class, () -> locationService.update(1L, dto));
        }

        @Test
        void testDelete() {
            when(repository.existsById(1L)).thenReturn(true);
            doNothing().when(repository).deleteById(1L);
            locationService.delete(1L);
            verify(repository, times(1)).existsById(1L);
            verify(repository, times(1)).deleteById(1L);
        }

        @Test
        void testDeleteNotFound() {
            when(repository.existsById(1L)).thenReturn(false);
            assertThrows(ResourceNotFoundException.class, () -> locationService.delete(1L));
        }
    }
