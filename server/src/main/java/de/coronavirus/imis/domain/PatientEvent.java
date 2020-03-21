package de.coronavirus.imis.domain;


import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class PatientEvent {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Institution testInstitution;

    @ManyToOne
    private Illness illness;

    private Timestamp eventTimestamp;

    @Enumerated(EnumType.STRING)
    private PatientEvent eventType;

    private String comment;
    private String accomodation;


}


