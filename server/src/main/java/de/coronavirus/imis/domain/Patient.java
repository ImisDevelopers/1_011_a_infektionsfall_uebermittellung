package de.coronavirus.imis.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import de.coronavirus.imis.domain.ExposureContact;
import de.coronavirus.imis.repositories.ExposureContactRepository;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class Patient {
	@OneToMany(mappedBy = "patient")
	@OrderBy("eventTimestamp asc")
	List<PatientEvent> events;
	@Id
	private String id;
	private String lastName;
	private String firstName;
	private String gender;
	private String nationality;
	private LocalDate dateOfBirth;
	private LocalDate dateOfDeath;
	private String email;
	private String phoneNumber;
	// Address
	private String street;
	private String houseNumber;
	private String zip;
	private String city;
	private String country;
	// Current Stay (If different from address)
	private String stayStreet;
	private String stayHouseNumber;
	private String stayZip;
	private String stayCity;
	private String stayCountry;
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

	@JsonIgnore
	@OneToOne(mappedBy = "contact")
	private ExposureContact exposureSourceContact;
	@JsonIgnore
	@OneToMany(mappedBy = "source")
	private List<ExposureContact> exposureContacts;

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
	// Hospitalization TODO: Put into seperate entity?
	private LocalDate dateOfHospitalization;
	private LocalDate dateOfIllness;
	private Boolean onIntensiveCareUnit;

	private LocalDate quarantineUntil;

	@ApiModel("PatientIdentity")
	@Getter
	@Setter
	public static class IdentityView {
		private String id;
		private String firstName;
		private String lastName;
	}
}
