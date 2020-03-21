package de.coronavirus.imis.file_storage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private String reportPath = "/reports/";

    FileStorageService(){

    }

    public void addFileById(String _id, MultipartFile file) throws Exception {

        // we do not safe the documents with the unique id as name, to maintain the original file name,
        // without risking double file identifiers. The folder location is unique
        String subdirectory = "/" + _id + "/";
        String full_directory = this.reportPath + subdirectory;
        Path directory = this.getFolderPath( full_directory );

        // store file on server disk
        byte[] bytes = file.getBytes();
        Path file_path = Paths.get(directory + file.getOriginalFilename());
        Files.write(file_path, bytes);
    }

    public File getFileById(String _id) throws FileNotFoundException {
        // create path by Id
        String subdirectory = "/" + _id + "/";
        String full_directory = this.reportPath + subdirectory;

        // get the files in directory
        File folder = new File(full_directory);
        File [] files = folder.listFiles();

        // the folder should only have a single file
        if (files.length >= 1) {
            // if a file is found return file
            return files[1];
        }

        throw new FileNotFoundException("The file by the identifier: " + _id + " was not found");
    }

    public void replaceFileById(String _id, MultipartFile file) throws IOException {
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
    }

    public void deleteFileById(String _id) throws IOException {
        String subdirectory = "/" + _id + "/";
        String full_directory = this.reportPath + subdirectory;
        Path directory = this.getFolderPath( full_directory );

        // clean the directory
        File folder_to_wipe = new File(directory.toString());
        FileUtils.cleanDirectory(folder_to_wipe);
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

            if(bool){
                return path;
            }else{
                throw new FileNotFoundException("Directory could not be created, check permission to create directories");
            }
        }
    }


}
