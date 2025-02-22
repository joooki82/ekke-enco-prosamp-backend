package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.TestReportSampler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestReportSamplerRepository extends JpaRepository<TestReportSampler, Long> {
}
