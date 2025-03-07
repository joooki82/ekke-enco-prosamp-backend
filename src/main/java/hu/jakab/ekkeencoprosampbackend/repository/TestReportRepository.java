package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.TestReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestReportRepository extends JpaRepository<TestReport, Long> {
    Optional<TestReport> findByReportNumber(String reportNumber);
    boolean existsByReportNumber(String reportNumber);


}
