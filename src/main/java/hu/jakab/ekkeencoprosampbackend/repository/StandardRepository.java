package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.Standard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StandardRepository extends JpaRepository<Standard, Long> {
    Optional<Standard> findByIdentifier(String identifier);
    boolean existsByIdentifier(String identifier);
}
