package de.coronavirus.imis.domain;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@JsonDeserialize(builder = Patient.PatientBuilder.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {


    @Id
    private String id;

    private String lastName;
    private String firstName;
    private String gender;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private String street;
    private String zip;
    private String houseNumber;
    private String city;
    private String comment;
    private String occupation;
    private String insuranceCompany;
    private String insuranceMembershipNumber;
    private boolean confirmed;

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    List<PatientEvent> events;

    @OneToMany
    private List<Illness> preExistingConditions;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class PatientBuilder {

    }


}
