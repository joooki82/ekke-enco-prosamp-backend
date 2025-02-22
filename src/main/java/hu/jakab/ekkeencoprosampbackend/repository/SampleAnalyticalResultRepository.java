package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.SampleAnalyticalResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleAnalyticalResultRepository extends JpaRepository<SampleAnalyticalResult, Long> {
}
