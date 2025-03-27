package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.SampleAnalyticalResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SampleAnalyticalResultRepository extends JpaRepository<SampleAnalyticalResult, Long> {
    Optional<SampleAnalyticalResult> findBySampleContaminantId(Long sampleContaminantId);

}
