package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.TestReport;
import hu.jakab.ekkeencoprosampbackend.model.TestReportSampler;
import hu.jakab.ekkeencoprosampbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestReportSamplerRepository extends JpaRepository<TestReportSampler, Long> {
    List<TestReportSampler> findByTestReport(TestReport testReport);

    List<TestReportSampler> findByUser(User user);

    boolean existsByTestReportAndUser(TestReport testReport, User user);

    void deleteByTestReportAndUser(TestReport testReport, User user);

}
