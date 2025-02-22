package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.ContaminantGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaminantGroupRepository extends JpaRepository<ContaminantGroup, Long> {
    Optional<ContaminantGroup> findByName(String name);
}
