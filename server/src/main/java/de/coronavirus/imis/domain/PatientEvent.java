package de.coronavirus.imis.domain;


import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Entity
@Value
@JsonDeserialize(builder = PatientEvent.PatientEventBuilder.class)
@Builder
public class PatientEvent {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
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

    @JsonPOJOBuilder(withPrefix = "")
    public static final class PatientEventBuilder {
        // needed for jackson deserialization
    }


}


