package de.coronavirus.imis.domain;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
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

    @OneToMany(mappedBy = "patient")
    List<PatientEvent> events;

    @OneToMany
    private List<Illness> preExistingConditions;


}
