package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.Contaminant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaminantRepository extends JpaRepository<Contaminant, Long> {
    Optional<Contaminant> findByName(String name);
}
