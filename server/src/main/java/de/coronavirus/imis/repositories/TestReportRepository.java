package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.TestReport;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/***
 * Repository for file storage.
 */
@Repository
public interface TestReportRepository extends JpaRepository<TestReport, String> {
    Optional<TestReport> findByTestId(String testId);
}
