package de.coronavirus.imis.domain;


import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Patient {
    @Id
    @GeneratedValue
    private Long id;

    private String lastName;
    private String firstName;
    private String gender;
    private Date dateOfBirth;
    private String email;
    private String phoneNumber;
    private String street;
    private Integer postalCode;
    private String houseNumber;
    private String city;
    private String comment;
    private String occupation;

    @OneToMany(mappedBy = "patient")
    List<PatientEvent> events;

    @OneToMany
    private List<Illness> preExistingConditions;


}
