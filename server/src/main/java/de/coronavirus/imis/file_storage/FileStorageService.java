package de.coronavirus.imis.file_storage;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private String reportPath = "/reports/";

    FileStorageService(){

    }

    public Path addFileById(String _id, MultipartFile file) throws Exception {

        // we do not safe the documents with the unique id as name, to maintain the original file name,
        // without risking double file identifiers. The folder location is unique
        String subdirectory = "/" + _id + "/";
        String full_directory = this.reportPath + subdirectory;
        Path directory = this.getFolderPath( full_directory );

        // store file on server disk
        byte[] bytes = file.getBytes();
        Path file_path = Paths.get(directory + file.getOriginalFilename());
        Files.write(file_path, bytes);

        // return file path
        return file_path;
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

    public Boolean deleteFileByPath(Path path){
        // create file from path
        File file = new File (path.toString());
        // delete file
        return file.delete();
    }

    public Boolean deleteFileByPath(String path){
        // create file from string
        File file = new File (path);
        // delete file
        return file.delete();
    }

    public File getFileByPath (Path path) throws FileNotFoundException {
        // create file from path
        File file = new File(path.toString());

        // check if file exists
        boolean exists = file.exists();

        if(exists) {
            return file;
        } else {
            throw new FileNotFoundException("The file with the path: " + path + " was not found");
        }

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
