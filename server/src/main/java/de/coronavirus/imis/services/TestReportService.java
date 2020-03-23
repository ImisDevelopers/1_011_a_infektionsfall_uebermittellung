package de.coronavirus.imis.services;

import de.coronavirus.imis.domain.TestReport;
import de.coronavirus.imis.repositories.TestReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
     * Returns a test report by its id from the database.
     * @param id: The id of the desired test report.
     * @return the desired test report.
     */
    public Optional<TestReport> findTestReportById(String id) {
        return testReportRepository.findById(id);
    }

    /***
     * Creates a test report and saves it in the database.
     * @param id: Id of the test report.
     * @param file: Binary data of the test report.
     */
    public TestReport addTestReport(String id, byte[] file) {
        TestReport testReport = new TestReport(id, file);
        testReportRepository.save(testReport);
        return testReport;
    }
}
