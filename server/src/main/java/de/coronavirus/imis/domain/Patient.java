package de.coronavirus.imis.domain;


import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Patient {
    @Id
    private String id;

    private String lastName;
    private String firstName;
    private String gender;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;

    private String email;
    private String phoneNumber;

    // Address
    private String street;
    private String houseNumber;
    private String zip;
    private String city;

    // Current Stay (If different from address)
    private String stayStreet;
    private String stayHouseNumber;
    private String stayZip;
    private String stayCity;

    private String insuranceCompany;
    private String insuranceMembershipNumber;
    private boolean confirmed;

    private String employer;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EventType patientStatus;

    private Boolean fluImmunization;
    private String speedOfSymptomsOutbreak;
    @Convert(converter = StringListConverter.class)
    private List<String> symptoms;

    private Boolean coronaContacts;
    @Convert(converter = StringListConverter.class)
    private List<String> riskAreas;

    private Boolean weakenedImmuneSystem;
    @Convert(converter = StringListConverter.class)
    private List<String> preIllnesses;

    @Enumerated(EnumType.STRING)
    private RiskOccupation riskOccupation;

    private String comment;
    private String occupation;

    private OffsetDateTime creationTimestamp;

    @OneToMany(mappedBy = "patient")
    List<PatientEvent> events;

    // Hospitalization TODO: Put into seperate entity?
    private LocalDate dateOfHospitalization;
    private LocalDate dateOfIllness;
    private Boolean onIntensiveCareUnit;
}
