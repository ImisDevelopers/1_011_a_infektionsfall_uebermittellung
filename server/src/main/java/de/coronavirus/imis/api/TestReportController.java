package de.coronavirus.imis.api;

import de.coronavirus.imis.domain.TestReport;
import de.coronavirus.imis.services.TestReportService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test_reports")
@RequiredArgsConstructor
public class TestReportController {

    // Instance of the storage service.
    private final TestReportService testReportService;

    /***
     * Endpoint to receive a test report.
     * @param testId: The testId of the test report to retrieve.
     * @return The test report.
     */
    @GetMapping("/{testId}")
    public ResponseEntity<TestReport> getTestReport(@PathVariable String testId) {
        return testReportService
                .findTestReportByTestId(testId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /***
     * Endpoint to receive all test reports.
     * @return All saved test reports..
     */
    @GetMapping()
    public List<TestReport> getAllTestReports() {
        return testReportService.getAllTestReports();
    }
}
