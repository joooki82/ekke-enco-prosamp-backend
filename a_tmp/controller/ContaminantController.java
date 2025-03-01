package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.controller.base.BaseController;
import hu.jakab.ekkeencoprosampbackend.dto.request.ContaminantRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.ContaminantResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.ContaminantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contaminants")
public class ContaminantController extends BaseController<ContaminantRequestDTO, ContaminantResponseDTO, Long> {

    private static final Logger logger = LoggerFactory.getLogger(ContaminantController.class);
    private final ContaminantService service;

    @Autowired
    public ContaminantController(ContaminantService service) {
        this.service = service;
    }

    @Override
    public List<ContaminantResponseDTO> getAllEntities() {
        logger.info("Fetching all contaminants");
        return service.getAll();
    }

    @Override
    public Optional<ContaminantResponseDTO> getEntityById(Long id) {
        logger.info("Fetching contaminant by ID: {}", id);
        return service.getById(id);
    }

    @Override
    public ContaminantResponseDTO createEntity(ContaminantRequestDTO dto) {
        logger.info("Creating a new contaminant");
        return service.save(dto);
    }

    @Override
    public Optional<ContaminantResponseDTO> updateEntity(Long id, ContaminantRequestDTO dto) {
        logger.info("Updating contaminant with ID: {}", id);
        return service.update(id, dto);
    }

    @Override
    public boolean deleteEntity(Long id) {
        logger.info("Deleting contaminant with ID: {}", id);
        return service.delete(id);
    }
}
