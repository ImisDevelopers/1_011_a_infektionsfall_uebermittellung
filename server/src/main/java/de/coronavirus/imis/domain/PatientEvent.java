package de.coronavirus.imis.domain;


import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class PatientEvent {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonManagedReference
    private Patient patient;

    @ManyToOne
    private Doctor responsibleDoctor;

    @Enumerated(EnumType.STRING)
    private Illness illness;

    @ManyToOne(fetch = FetchType.EAGER)
    private LabTest labTest;

    private Timestamp eventTimestamp;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String comment;
    private String accomodation;
}


