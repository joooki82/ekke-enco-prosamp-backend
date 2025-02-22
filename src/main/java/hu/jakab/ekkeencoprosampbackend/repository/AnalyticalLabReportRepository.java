package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.AnalyticalLabReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnalyticalLabReportRepository extends JpaRepository<AnalyticalLabReport, Long> {
    Optional<AnalyticalLabReport> findByReportNumber(String reportNumber);
}
