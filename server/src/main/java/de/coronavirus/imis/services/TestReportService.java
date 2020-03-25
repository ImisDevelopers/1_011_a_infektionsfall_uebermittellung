package de.coronavirus.imis.services;

import de.coronavirus.imis.domain.TestReport;
import de.coronavirus.imis.repositories.TestReportRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/***
 * Implements functionality to save files related to a test to the database.
 */
@Service
public class TestReportService {

    // The data repository.
    private TestReportRepository testReportRepository;

    /***
     * Initializes the service with its repository.
     * @param testReportRepository: The data repository.
     */
    public TestReportService(TestReportRepository testReportRepository) {
        this.testReportRepository = testReportRepository;
    }

    /***
     * Returns all saved test reports.
     * @return all saved test reports.
     */
    public List<TestReport> getAllTestReports() {
        return testReportRepository.findAll();
    }

    /***
     * Returns a test report by its testId from the database.
     * @param testId: The testId of the desired test report.
     * @return the desired test report.
     */
    public Optional<TestReport> findTestReportByTestId(String testId) {
        return testReportRepository.findByTestId(testId);
    }

    /***
     * Creates a test report and saves it in the database.
     * @param testId: Id of the test report.
     * @param file: Binary data of the test report.
     */
    public TestReport addTestReport(String testId, byte[] file) {
        TestReport testReport = new TestReport(testId, file);
        testReportRepository.save(testReport);
        return testReport;
    }
}
