package de.coronavirus.imis.repositories;

import de.coronavirus.imis.domain.TestReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/***
 * Repository for file storage.
 */
@Repository
public interface TestReportRepository extends JpaRepository<TestReport, String>
{}
