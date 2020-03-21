package de.coronavirus.imis.file_storage;

import de.coronavirus.imis.domain.TestReport;
import org.springframework.stereotype.Service;

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
     * @param file: Binary data of the test report.
     */
    void createTestReport(Long id, byte[] file) {
        TestReport testReport = new TestReport(id, file);
        fileStorageDataRepository.save(testReport);
    }

    /***
     * Updates a test report in the database.
     * @param id: Id of the test report.
     * @param file: Binary data of the test report.
     */
    void updateTestReport(Long id, byte[] file) {
        Optional<TestReport> optionalTestReport = getTestReport(id);

        optionalTestReport.ifPresent(testReport -> {
            testReport.setFile(file);
            fileStorageDataRepository.save(testReport);
        });
    }

    /***
     * Deletes a test report in the database.
     * @param id: Id of the test report.
     */
    void deleteTestReport(Long id){
        fileStorageDataRepository.deleteById(id);
    }
}
