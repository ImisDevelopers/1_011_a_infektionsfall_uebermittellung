package de.coronavirus.imis.file_storage;

import de.coronavirus.imis.domain.TestReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileNotFoundException;

@RestController
public class FileStorageController {

    // Instance of the storage service.
    private final FileStorageService fileStorageService;

    @Autowired
    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }


    /***
     * Endpoint to upload a file.
     * @param file: The file to upload.
     * @param testId: The id of the test report the file belongs to.
     * @param redirectAttributes: Used for redirection.
     * @return A redirection.
     */
    @PostMapping("/test_report/{testId}")
    public String uploadFile(
            @RequestParam("file") MultipartFile file,
            @PathVariable("testId") Long testId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            fileStorageService.addFileById(testId, file);
        } catch (Exception e) {
            return "The following error was encountered during file saving: "
                    + e;
        }

        redirectAttributes.addFlashAttribute(
                "message",
                "You successfully uploaded "
                        + file.getOriginalFilename()
                        + "!"
        );

        return "redirect:/";
    }

    /***
     * Endpoint to update a file.
     * @param file: The file to update.
     * @param testId: The id of the test report the file belongs to.
     * @param redirectAttributes: Used for redirection.
     * @return A redirection.
     */
    @PutMapping("/test_report/{testId}")
    public String updateFile(
            @RequestParam("file") MultipartFile file,
            @PathVariable("testId") Long testId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            fileStorageService.replaceFileById(testId, file);
        } catch (Exception e) {
            return "The following error was encountered during file update: "
                    + e;
        }

        redirectAttributes.addFlashAttribute(
                "message",
                "You successfully uploaded "
                        + file.getOriginalFilename()
                        + "!"
        );

        return "redirect:/";
    }

    /***
     * Endpoint to receive a test report.
     * @param testId: The id of the test report to retrieve.
     * @return The test report.
     */
    @GetMapping("/test_report/{testId}")
    public ResponseEntity<TestReport> getTestReport(@PathVariable Long testId) {
        TestReport testReport;

        try {
            testReport = fileStorageService.getFileById(testId);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok().body(testReport);
    }


    /***
     * Deletes a test report.
     * @param testId: The id of the test report to delete.
     * @return A message stating the success of the deletion.
     */
    @DeleteMapping("/test_report/{testId}")
    public ResponseEntity<HttpStatus> deleteTestReport(
            @PathVariable Long testId
    ) {
        fileStorageService.deleteFileById(testId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
