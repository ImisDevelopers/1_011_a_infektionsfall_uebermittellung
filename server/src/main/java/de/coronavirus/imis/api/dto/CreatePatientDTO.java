package de.coronavirus.imis.api.dto;

import java.util.List;

import lombok.Data;

@Data
public class CreatePatientDTO {
    private String lastName;
    private String firstName;
    private String gender;
    private String dateOfBirth;

    private String email;
    private String phoneNumber;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;

    private String insuranceCompany;
    private String insuranceMembershipNumber;

    private Boolean fluImmunization;
    private String speedOfSymptomsOutbreak;
    private List<String> symptoms;

    private Boolean coronaContacts;
    private List<String> riskAreas;

    private Boolean weakenedImmuneSystem;
    private List<String> preIllnesses;
}
