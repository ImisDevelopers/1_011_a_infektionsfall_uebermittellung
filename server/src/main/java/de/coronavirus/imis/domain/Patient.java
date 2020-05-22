package de.coronavirus.imis.domain;


import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.SortableField;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;
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
@Indexed
public class Patient {
	@OneToMany(mappedBy = "patient")
	@OrderBy("eventTimestamp asc")
	List<PatientEvent> events;
	@Id
	@SortableField
	private String id;
	@SortableField
	@Field
	private String lastName;
	@Field
	private String firstName;
	private String gender;
	private String nationality;
	private LocalDate dateOfBirth;
	private LocalDate dateOfDeath;
	@Field
	private String email;
	@Field
	private String phoneNumber;
	// Address
	@Field
	private String street;
	@Field
	private String houseNumber;
	@SortableField
	@Field
	private String zip;
	@SortableField
	@Field
	private String city;
	private String country;
	// Current Stay (If different from address)
	private String stayStreet;
	private String stayHouseNumber;
	private String stayZip;
	private String stayCity;
	private String stayCountry;
	@Field
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
	@OneToMany(mappedBy = "contact")
	private List<InfectionSource> infectionSource;
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
