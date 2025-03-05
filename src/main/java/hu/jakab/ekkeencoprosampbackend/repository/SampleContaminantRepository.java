package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.Contaminant;
import hu.jakab.ekkeencoprosampbackend.model.Sample;
import hu.jakab.ekkeencoprosampbackend.model.SampleContaminant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SampleContaminantRepository extends JpaRepository<SampleContaminant, Long> {

    List<SampleContaminant> findBySample(Sample sample);

    Optional<SampleContaminant> findBySampleAndContaminant(Sample sample, Contaminant contaminant);

}
