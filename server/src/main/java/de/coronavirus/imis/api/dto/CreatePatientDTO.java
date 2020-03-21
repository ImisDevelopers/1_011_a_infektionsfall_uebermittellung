package de.coronavirus.imis.api.dto;

import java.util.List;

import lombok.Data;

@Data
public class CreatePatientDTO {

    public String gender;
    public String birthDate;
    public String lastName;
    public String firstName;
    public String street;
    public String houseNumber;
    public String zip;
    public String city;
    public String email;
    public String phone;
    public String insuranceCompany;
    public String insuranceMembershipNumber;
    public String cough;
    public String fatigue;
    public String fever;
    public String shortnessOfBreath;
    public String soreThroat;
    public String cold;
    public String limbPain;
    public String headaches;
    public String diarrhea;
    public String symptoms;
    public List<String> riskAreas;
    public String coronaContacts;
    public List<String> preIllnesses;
    public String weakenedImmuneSystem;
    public String fluImmunization;

}
