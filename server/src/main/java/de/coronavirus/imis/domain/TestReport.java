package de.coronavirus.imis.domain;


import java.io.Serializable;
import java.nio.file.Path;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;


/***
 * An entity representing a test report.
 */
@Entity
@Table(name = "test_report")
@NoArgsConstructor
public class TestReport implements Serializable {

    // ID of the test.
    @Id
    public Long id;

    // Path to the location of the report in the file system.
    public String filePath;

    /***
     * Initalizes a new test report.
     * @param id: Id of the report.
     * @param filePath: File path of the report.
     */
    public TestReport(Long id, Path filePath) {
        this.id = id;
        this.filePath = filePath.toAbsolutePath().toString();
    }

    /***
     * Sets the id of the report.
     * @param id: Id of the report.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /***
     * Sets the file path of the report.
     * @param filePath: File path to the file system.
     */
    public void setFilePath(Path filePath) {
        this.filePath = filePath.toAbsolutePath().toString();
    }
}

