package hu.jakab.ekkeencoprosampbackend.controller;


import hu.jakab.ekkeencoprosampbackend.dto.location.LocationCreatedDTO;
import hu.jakab.ekkeencoprosampbackend.dto.location.LocationRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.location.LocationResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.LocationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {


    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);
    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<LocationResponseDTO>> getAllLocations() {
        logger.info("Fetching all locations");
        List<LocationResponseDTO> Locations = service.getAll();
        return Locations.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(Locations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> getLocationById(@PathVariable Long id) {
        logger.info("Fetching location by ID: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<LocationCreatedDTO> createLocation(@RequestBody @Valid LocationRequestDTO dto) {
        logger.info("Creating a new location with name: {}", dto.getName());
        LocationCreatedDTO createdLocation = service.save(dto);
        return ResponseEntity.status(201).body(createdLocation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> updateLocation(@PathVariable Long id, @RequestBody @Valid LocationRequestDTO dto) {
        logger.info("Updating location (ID: {}) with new details", id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        logger.info("Deleting location with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
