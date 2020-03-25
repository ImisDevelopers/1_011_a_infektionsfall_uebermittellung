package de.coronavirus.imis.api;

import com.google.cloud.storage.Blob;
import de.coronavirus.imis.services.TestReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<HttpStatus> uploadTestReport(
            @RequestParam("file") MultipartFile file,
            @PathVariable("id") String id
    ) {
        try {
            testReportService.addTestReport(id, file.getBytes());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }

        return ResponseEntity.ok().build();
    }

    /***
     * Endpoint to receive a test report.
     * @param id: The id of the test report to retrieve.
     * @return The test report.
     */
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getTestReport(
            @PathVariable String id
    ) {
        byte[] testReport = testReportService
                .findTestReportById(id)
                .getContent();

        return ResponseEntity.ok().body(testReport);
    }

    /***
     * Endpoint to receive all test reports.
     * @return All saved test reports..
     */
    @GetMapping()
    public List<Blob> getAllTestReports() {
        return testReportService.getAllTestReports();
    }
}
