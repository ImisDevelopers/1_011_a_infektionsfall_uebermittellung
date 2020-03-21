package de.coronavirus.imis.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


/***
 * An entity representing a test report.
 */
@Entity
@Table(name = "test_report")
public class TestReport {

    // ID of the test.
    @Id
    public Long id;

    @Lob
    public byte[] file;

    /***
     * Initalizes a new test report.
     * @param id: Id of the report.
     * @param file: Binary data of the report.
     */
    public TestReport(Long id, byte[] file) {
        this.id = id;
        this.file = file;
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
     * @param file: Binary data of the report.
     */
    public void setFilePath(byte[] file){
        this.file = file;
    }
}

