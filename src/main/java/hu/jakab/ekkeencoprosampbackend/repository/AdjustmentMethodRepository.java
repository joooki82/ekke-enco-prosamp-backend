package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdjustmentMethodRepository extends JpaRepository<AdjustmentMethod, Long> {
    Optional<AdjustmentMethod> findByCode(String code);
}

