package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.SamplingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SamplingTypeRepository extends JpaRepository<SamplingType, Long> {
    Optional<SamplingType> findByCode(String code);
}
