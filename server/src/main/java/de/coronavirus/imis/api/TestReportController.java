package de.coronavirus.imis.api;

import de.coronavirus.imis.domain.TestReport;
import de.coronavirus.imis.services.TestReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/test_reports")
@RequiredArgsConstructor
public class TestReportController {

    // Instance of the storage service.
    private final TestReportService testReportService;

    /***
     * Endpoint to upload a file.
     * @param file: The file to upload.
     * @param id: The id of the test report the file belongs to.
     * @return A redirection.
     */
    @PostMapping("/{id}")
    public ResponseEntity<TestReport> uploadTestReport(
            @RequestParam("file") MultipartFile file,
            @PathVariable("id") String id
    ) {
        TestReport testReport;
        try {
            testReport = testReportService
                    .addTestReport(id, file.getBytes());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }

        return ResponseEntity.ok().body(testReport);
    }

    /***
     * Endpoint to receive a test report.
     * @param id: The id of the test report to retrieve.
     * @return The test report.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TestReport> getTestReport(
            @PathVariable String id
    ) {
        return testReportService
                .findTestReportById(id)
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
