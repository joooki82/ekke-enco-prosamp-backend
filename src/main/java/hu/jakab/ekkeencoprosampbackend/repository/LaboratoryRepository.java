package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LaboratoryRepository extends JpaRepository<Laboratory, Long> {
    Optional<Laboratory> findByName(String name);
}
