package de.coronavirus.imis.file_storage;

import de.coronavirus.imis.domain.TestReport;
import de.coronavirus.imis.example.FileStorageDataRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileStorageDataService {

    private FileStorageDataRepository fileStorageDataRepository;

    public FileStorageDataService(FileStorageDataRepository fileStorageDataRepository) {
        this.fileStorageDataRepository = fileStorageDataRepository;
    }

    public TestReport getTestReport(Long id) {
        return fileStorageDataRepository.findById(id);
    }

    public TestReport createTestReport(Long id, String filePath) {
        TestReport testReport = new TestReport();
        testReport.setId(id);
        testReport.setFilePath(filePath);
        return fileStorageDataRepository.save(testReport);
    }

    public void deleteTestReport(Long id){
        TestReport testReport = getTestReport(id);
        fileStorageDataRepository.delete(testReport);
    }

}
