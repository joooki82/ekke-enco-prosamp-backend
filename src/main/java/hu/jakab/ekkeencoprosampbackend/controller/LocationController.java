package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.controller.base.BaseController;
import hu.jakab.ekkeencoprosampbackend.dto.request.LocationRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.LocationResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationController extends BaseController<LocationRequestDTO, LocationResponseDTO, Long> {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    private final LocationService service;

    @Autowired
    public LocationController(LocationService service) {
        this.service = service;
    }

    @Override
    public List<LocationResponseDTO> getAllEntities() {
        logger.info("Fetching all locations");
        return service.getAll();
    }

    @Override
    public Optional<LocationResponseDTO> getEntityById(Long id) {
        logger.info("Fetching location by ID: {}", id);
        return service.getById(id);
    }

    @Override
    public LocationResponseDTO createEntity(LocationRequestDTO dto) {
        logger.info("Creating a new location");
        return service.save(dto);
    }

    @Override
    public Optional<LocationResponseDTO> updateEntity(Long id, LocationRequestDTO dto) {
        logger.info("Updating location with ID: {}", id);
        return service.update(id, dto);
    }

    @Override
    public boolean deleteEntity(Long id) {
        logger.info("Deleting location with ID: {}", id);
        return service.delete(id);
    }
}
