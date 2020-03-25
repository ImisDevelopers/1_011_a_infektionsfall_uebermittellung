package de.coronavirus.imis.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.*;


/***
 * An entity representing a test report.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestReport {

    @Id
    private String testId;

    // Binary of the test files.
    @Lob
    private byte[] file;
}

