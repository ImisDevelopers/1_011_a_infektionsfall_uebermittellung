package de.coronavirus.imis.file_storage;

import de.coronavirus.imis.domain.TestReport;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Optional;

/***
 * Implements functionality to save files related to a test to the database.
 */
@Service
public class FileStorageDataService {

    // The data repository.
    private FileStorageDataRepository fileStorageDataRepository;

    /***
     * Initializes the service with its repository.
     * @param fileStorageDataRepository: The data repository.
     */
    public FileStorageDataService(
            FileStorageDataRepository fileStorageDataRepository
    ) {
        this.fileStorageDataRepository = fileStorageDataRepository;
    }

    /***
     * Returns a test report by its id from the database.
     * @param id: The id of the desired test report.
     * @return the desired test report.
     */
    Optional<TestReport> getTestReport(Long id) {
        return fileStorageDataRepository.findById(id);
    }

    /***
     * Creates a test report and saves it in the database.
     * @param id: Id of the test report.
     * @param filePath: Path of the test report into the file system.
     */
    void createTestReport(Long id, Path filePath) {
        TestReport testReport = new TestReport(id, filePath);
        fileStorageDataRepository.save(testReport);
    }

    /***
     * Updates a test report in the database.
     * @param id: Id of the test report.
     * @param filePath: Path to the test report.
     */
    void updateTestReport(Long id, Path filePath) {
        Optional<TestReport> optionalTestReport = getTestReport(id);

        optionalTestReport.ifPresent(testReport -> {
            testReport.setFilePath(filePath);
            fileStorageDataRepository.save(testReport);
        });
    }

    /***
     * Deletes a test repoert in the database.
     * @param id: Id of the test report.
     */
    public void deleteTestReport(Long id){
        fileStorageDataRepository.deleteById(id);
    }
}
