package de.coronavirus.imis.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.nio.file.Path;


/***
 * An entity representing a test report.
 */
@Entity
@Table(name = "test_report")
public class TestReport {

    // ID of the test.
    @Id
    public Long id;

    // Path to the location of the report in the file system.
    public Path filePath;

    /***
     * Initalizes a new test report.
     * @param id: Id of the report.
     * @param filePath: File path of the report.
     */
    public TestReport(Long id, Path filePath) {
        this.id = id;
        this.filePath = filePath;
    }

    /***
     * Sets the id of the report.
     * @param id: Id of the report.
     */
    public void setId(Long id){
        this.id = id;
    }

    /***
     * Sets the file path of the report.
     * @param filePath: File path to the file system.
     */
    public void setFilePath(Path filePath){
        this.filePath = filePath;
    }
}

