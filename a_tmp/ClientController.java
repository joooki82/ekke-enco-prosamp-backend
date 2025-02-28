//package hu.jakab.ekkeencoprosampbackend.controller;
//
//import hu.jakab.ekkeencoprosampbackend.controller.base.BaseController;
//import hu.jakab.ekkeencoprosampbackend.dto.request.ClientRequestDTO;
//import hu.jakab.ekkeencoprosampbackend.dto.response.ClientResponseDTO;
//import hu.jakab.ekkeencoprosampbackend.service.ClientService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/clients")
//public class ClientController extends BaseController<ClientRequestDTO, ClientResponseDTO, Long> {
//
//    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
//
//    private final ClientService service;
//
//    @Autowired
//    public ClientController(ClientService service) {
//        this.service = service;
//    }
//
//    @Override
//    public List<ClientResponseDTO> getAllEntities() {
//        logger.info("Fetching all clients");
//        return service.getAll();
//    }
//
//    @Override
//    public Optional<ClientResponseDTO> getEntityById(Long id) {
//        logger.info("Fetching client by ID: {}", id);
//        return service.getById(id);
//    }
//
//    @Override
//    public ClientResponseDTO createEntity(ClientRequestDTO dto) {
//        logger.info("Creating a new client: {}", dto.getName());
//        return service.save(dto);
//    }
//    @Override
//    public Optional<ClientResponseDTO> updateEntity(Long id, ClientRequestDTO dto) {
//        logger.info("Updating client with ID: {}", id);
//        return service.update(id, dto);
//    }
//
//    @Override
//    public boolean deleteEntity(Long id) {
//        logger.info("Deleting client with ID: {}", id);
//        return service.delete(id);
//    }
//}
