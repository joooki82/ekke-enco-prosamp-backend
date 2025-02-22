package hu.jakab.ekkeencoprosampbackend.repository;

import hu.jakab.ekkeencoprosampbackend.model.Standard;
import hu.jakab.ekkeencoprosampbackend.model.TestReport;
import hu.jakab.ekkeencoprosampbackend.model.TestReportStandard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestReportStandardRepository extends JpaRepository<TestReportStandard, Long> {
    List<TestReportStandard> findByTestReport(TestReport testReport);

    List<TestReportStandard> findByStandard(Standard standard);

    boolean existsByTestReportAndStandard(TestReport testReport, Standard standard);

    void deleteByTestReportAndStandard(TestReport testReport, Standard standard);

}
