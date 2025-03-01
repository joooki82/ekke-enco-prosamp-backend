package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.controller.base.BaseController;
import hu.jakab.ekkeencoprosampbackend.dto.request.ContaminantGroupRequestDTO;
import hu.jakab.ekkeencoprosampbackend.dto.response.ContaminantGroupResponseDTO;
import hu.jakab.ekkeencoprosampbackend.service.ContaminantGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contaminant-groups")
public class ContaminantGroupController extends BaseController<ContaminantGroupRequestDTO, ContaminantGroupResponseDTO, Long> {

    private static final Logger logger = LoggerFactory.getLogger(ContaminantGroupController.class);

    private final ContaminantGroupService service;

    @Autowired
    public ContaminantGroupController(ContaminantGroupService service) {
        this.service = service;
    }

    @Override
    public List<ContaminantGroupResponseDTO> getAllEntities() {
        logger.info("Fetching all contaminant groups");
        return service.getAll();
    }

    @Override
    public Optional<ContaminantGroupResponseDTO> getEntityById(Long id) {
        logger.info("Fetching contaminant group by ID: {}", id);
        return service.getById(id);
    }

    @Override
    public ContaminantGroupResponseDTO createEntity(ContaminantGroupRequestDTO dto) {
        logger.info("Creating a new contaminant group: {}", dto.getName());
        return service.save(dto);
    }

    @Override
    public Optional<ContaminantGroupResponseDTO> updateEntity(Long id, ContaminantGroupRequestDTO dto) {
        logger.info("Updating contaminant group with ID: {}", id);
        return service.update(id, dto);
    }

    @Override
    public boolean deleteEntity(Long id) {
        logger.info("Deleting contaminant group with ID: {}", id);
        return service.delete(id);
    }
}
