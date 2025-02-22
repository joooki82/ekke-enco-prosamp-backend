package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.SampleContaminant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleContaminantRepository extends JpaRepository<SampleContaminant, Long> {
}
