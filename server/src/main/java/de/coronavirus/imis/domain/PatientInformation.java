package de.coronavirus.imis.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.coronavirus.imis.config.domain.User;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Data
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class PatientInformation {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;

	@OneToOne
	private User user;

	@ManyToOne
	private Patient patient;

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
	private Boolean fluImmunization;

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
	// Hospitalization TODO: Put into seperate entity?
	private LocalDate dateOfHospitalization;
	private LocalDate dateOfIllness;
	private Boolean onIntensiveCareUnit;
}
