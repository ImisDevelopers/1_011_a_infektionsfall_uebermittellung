package de.coronavirus.imis.file_storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

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
            @PathVariable("testId") String testId,
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
     * Endpoint to receive a test report.
     * @param testId: The id of the test report to retrieve.
     * @return The test report.
     */
    @GetMapping("/test_report/{testId}")
    public ResponseEntity<File> getTestReport(@PathVariable String testId) {
        File testReport;

        try {
            testReport = fileStorageService.getFileById(testId);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\""
                        + testReport.getName()
                        + "\""
        ).body(testReport);
    }

    /*
    @GetMapping("/test_report/file/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable UUID fileId) {
        Resource file = fileStorageService.getFile(fileId);

        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\""
                        + file.getFilename()
                        + "\""
        ).body(file);
    }
    */

    /***
     * Deletes a test report.
     * @param testId: The id of the test report to delete.
     * @return A message stating the success of the deletion.
     */
    @DeleteMapping("/test_report/{testId}")
    public ResponseEntity<HttpStatus> deleteTestReport(
            @PathVariable UUID testId
    ) {

        // fileStorageService.deleteTestReport(testId);

        return new ResponseEntity<>(HttpStatus.OK);
        // Resource file = fileStorageService.getFile(fileId);
    }

    /*
    @DeleteMapping("/test_report/file/{fileId}")
    public ResponseEntity<HttpStatus> deleteFile(@PathVariable UUID fileId) {

        // fileStorageService.deleteFile(fileId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
     */
}
