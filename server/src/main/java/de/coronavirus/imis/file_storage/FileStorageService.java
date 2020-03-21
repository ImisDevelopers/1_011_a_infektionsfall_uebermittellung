package de.coronavirus.imis.file_storage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import de.coronavirus.imis.domain.TestReport;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/***
 * Service class for saving uploaded files to the file system.
 */
@Service
public class FileStorageService {

    /***
     * Path for testReports.
     */
    private String reportPath = "/reports/";

    // Instance of the data storage service.
    private final FileStorageDataService fileStorageDataService;

    @Autowired
    public FileStorageService(FileStorageDataService fileStorageDataService) {
        this.fileStorageDataService = fileStorageDataService;
    }

    /***
     * Saves a file identified by an id.
     * @param _id: The identifier of the file.
     * @param file: The binary of the file.
     * @throws Exception
     */
    void addFileById(Long _id, MultipartFile file) throws Exception {

        // we do not safe the documents with the unique id as name, to maintain
        // the original file name,
        // without risking double file identifiers. The folder location is
        // unique
        String subdirectory = "/" + _id + "/";
        String full_directory = this.reportPath + subdirectory;
        Path directory = this.getFolderPath( full_directory );

        // store file on server disk
        byte[] bytes = file.getBytes();
        Path file_path = Paths.get(directory + file.getOriginalFilename());
        Files.write(file_path, bytes);

        fileStorageDataService.createTestReport(_id, file_path);
    }

    /***
     * Gets a file from the file system by an id.
     * @param _id: The identifier of the file.
     * @return The returned file.
     * @throws FileNotFoundException
     */
    File getFileById(Long _id) throws FileNotFoundException {
        Optional<TestReport> optionalTestReport =
                fileStorageDataService.getTestReport(_id);


        if (optionalTestReport.isPresent()) {
            TestReport testReport = optionalTestReport.get();

            // create path by Id
            String subdirectory = "/" + _id + "/";
            String full_directory = this.reportPath + subdirectory;

            // get the files in directory
            File folder = new File(testReport.filePath.toString());
            File [] files = folder.listFiles();

            // the folder should only have a single file
            assert files != null;
            if (files.length >= 1) {
                // if a file is found return file
                return files[1];
            }
        }


        throw new FileNotFoundException(
                "The file by the identifier: " + _id + " was not found"
        );
    }

    /***
     * Replace a file identified by an id.
     * @param _id: The identifier of the file.
     * @param file: The new file.
     * @throws IOException
     */
    void replaceFileById(Long _id, MultipartFile file) throws IOException {
        String subdirectory = "/" + _id + "/";
        String full_directory = this.reportPath + subdirectory;
        Path directory = this.getFolderPath( full_directory );

        // clean the directory
        File folder_to_wipe = new File(directory.toString());
        FileUtils.cleanDirectory(folder_to_wipe);

        // store file on server disk
        byte[] bytes = file.getBytes();
        Path file_path = Paths.get(directory + file.getOriginalFilename());
        Files.write(file_path, bytes);

        fileStorageDataService.updateTestReport(_id, file_path);
    }

    /***
     * Delete a file given its id.
     * @param _id: The unique identifier of a file to be deleted.
     * @throws IOException
     */
    void deleteFileById(Long _id) throws IOException {
        String subdirectory = "/" + _id + "/";
        String full_directory = this.reportPath + subdirectory;
        Path directory = this.getFolderPath( full_directory );

        // clean the directory
        File folder_to_wipe = new File(directory.toString());
        FileUtils.cleanDirectory(folder_to_wipe);

        fileStorageDataService.deleteTestReport(_id);
    }

    private Path getFolderPath(String string_path) throws FileNotFoundException {
        // create path object
        Path path = Paths.get(string_path);

        // if the path exits return path
        if (Files.exists(path)) {
            return path;

            // else create directory to path
        } else {

            File folder = new File(string_path);
            boolean bool = folder.mkdirs();

            if(bool) {
                return path;
            } else {
                throw new FileNotFoundException(
                        "Directory could not be created, check permission to " +
                                "create directories"
                );
            }
        }
    }
}
