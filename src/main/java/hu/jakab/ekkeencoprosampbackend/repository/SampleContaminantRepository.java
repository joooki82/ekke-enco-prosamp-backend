package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.SampleContaminant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SampleContaminantRepository extends JpaRepository<SampleContaminant, Long> {

    List<SampleContaminant> findBySampleId(Long sampleId);

    boolean existsBySampleIdAndContaminantId(Long sampleId, Long contaminantId);

    void deleteBySampleIdAndContaminantId(Long sampleId, Long contaminantId);

}
