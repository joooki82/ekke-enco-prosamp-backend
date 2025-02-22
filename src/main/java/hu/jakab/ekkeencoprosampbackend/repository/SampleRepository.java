package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SampleRepository extends JpaRepository<Sample, Long> {
    Optional<Sample> findBySampleIdentifier(String sampleIdentifier);
}
