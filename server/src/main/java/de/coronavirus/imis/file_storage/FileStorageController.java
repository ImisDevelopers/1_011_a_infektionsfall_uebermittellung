package de.coronavirus.imis.file_storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Rest interface for file uploads.
 */
@RestController
public class FileStorageController {

    private final FileStorageService fileStorageService;

    @Autowired
    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/test_report/{testId}")
    public String uploadFile(
            @RequestParam("file") MultipartFile file,
            @PathVariable("testId") UUID testId,
            RedirectAttributes redirectAttributes
    ) {
        // fileStorageService.store(file, fileId);
        redirectAttributes.addFlashAttribute(
                "message",
                "You successfully uploaded "
                        + file.getOriginalFilename()
                        + "!"
        );

        return "redirect:/";
    }

    @PutMapping("/test_report/{testId}")
    public String updateFile(
            @RequestParam("file") MultipartFile file,
            @PathVariable("testId") UUID testId,
            RedirectAttributes redirectAttributes
    ) {
        // fileStorageService.update(file, testId);
        redirectAttributes.addFlashAttribute(
                "message",
                "You successfully uploaded "
                        + file.getOriginalFilename()
                        + "!"
        );

        return "redirect:/";
    }

    @GetMapping("/test_report/{testId}")
    public ResponseEntity<Resource> getTestReport(@PathVariable UUID testId) {
        // Resource testReport = fileStorageService.getTestReport(testId);

        /*
        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\""
                        + testReport.getFilename()
                        + "\""
        ).body(testReport);
         */
    }


    @GetMapping("/test_report/file/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable UUID fileId) {
        // Resource file = fileStorageService.getFile(fileId);

        /*
        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\""
                        + file.getFilename()
                        + "\""
        ).body(file);
         */
    }

    @DeleteMapping("/test_report/{testId}")
    public ResponseEntity<HttpStatus> deleteTestReport(
            @PathVariable UUID testId
    ) {

        // fileStorageService.deleteTestReport(testId);

        return new ResponseEntity<>(HttpStatus.OK);
        // Resource file = fileStorageService.getFile(fileId);
    }

    @DeleteMapping("/test_report/file/{fileId}")
    public ResponseEntity<HttpStatus> deleteFile(@PathVariable UUID fileId) {

        // fileStorageService.deleteFile(fileId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
