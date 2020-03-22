package de.coronavirus.imis.domain;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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

    // Binary of the test files.
    @Lob
    public byte[] file;

    /***
     * Initializes a new test report.
     * @param id: Id of the report.
     * @param file: Binary of the test files.
     */
    public TestReport(Long id, byte[] file) {
        this.id = id;
        this.file = file;
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
     * @param file: Binary of the test files.
     */
    public void setFile(byte[] file) {
        this.file = file;
    }
}

