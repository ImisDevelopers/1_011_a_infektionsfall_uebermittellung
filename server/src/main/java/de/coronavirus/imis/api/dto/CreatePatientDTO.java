package de.coronavirus.imis.api.dto;

import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.RiskOccupation;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreatePatientDTO {
	private String lastName;
	private String firstName;
	private String gender;
	private String nationality;
	private String dateOfBirth;
	private String dateOfDeath;

	private String email;
	private String phoneNumber;
	private String street;
	private String houseNumber;
	private String zip;
	private String city;
	private String country;

	private String stayStreet;
	private String stayHouseNumber;
	private String stayZip;
	private String stayCity;
	private String stayCountry;

	private String insuranceCompany;
	private String insuranceMembershipNumber;
	private EventType patientStatus;
	private String employer;

	private Boolean fluImmunization;
	private String speedOfSymptomsOutbreak;
	private List<String> symptoms;

	private Boolean coronaContacts;
	private List<String> riskAreas;

	private Boolean weakenedImmuneSystem;
	private List<String> preIllnesses;
	private RiskOccupation riskOccupation;
	private String occupation;

	// Hospitalization
	private LocalDate dateOfHospitalization;
	private String dateOfIllness;
	private Boolean onIntensiveCareUnit;

	// Meldedatum
	private String dateOfReporting;

	private LocalDate quarantineUntil;
}
