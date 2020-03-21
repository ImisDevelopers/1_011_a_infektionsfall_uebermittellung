package de.coronavirus.imis.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Test {
    @Id
    @GeneratedValue
    private Long id;

    private String laborTestID;

    @ManyToOne
    private Institution laboratory;

    private TestStatus testStatus;


}
