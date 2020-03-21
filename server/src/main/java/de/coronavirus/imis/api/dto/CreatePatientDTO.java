package de.coronavirus.imis.api.dto;

import java.util.List;

import lombok.Data;

@Data
public class CreatePatientDTO {
    public String lastName;
    public String firstName;
    public String gender;
    public String dateOfBirth;

    public String email;
    public String phoneNumber;
    public String street;
    public String houseNumber;
    public String postalCode;
    public String city;

    public String insuranceCompany;
    public String insuranceMembershipNumber;

    public Boolean fluImmunization;
    public String speedOfSymptomsOutbreak;
    public List<String> symptoms;

    public Boolean coronaContacts;
    public List<String> riskAreas;

    public Boolean weakenedImmuneSystem;
    public List<String> preIllnesses;
}
