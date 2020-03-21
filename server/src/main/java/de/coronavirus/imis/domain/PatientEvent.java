package de.coronavirus.imis.domain;


import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
public class PatientEvent {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    private Patient patient;

/*    @ManyToOne
    private Institution testInstitution;*/

    @ManyToOne
    private Illness illness;

    @ManyToOne
    private LabTest labTest;

    private Timestamp eventTimestamp;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String comment;
    private String accomodation;


}


