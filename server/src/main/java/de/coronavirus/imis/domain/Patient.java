package de.coronavirus.imis.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class Patient {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;

	@OneToMany(mappedBy = "patient")
	List<PatientEvent> events;

	@OneToMany(mappedBy = "patient")
	List<PatientInformation> information;

	@OneToMany(mappedBy = "patient")
	List<PatientCase> cases;

	@Enumerated(EnumType.STRING)
	@NotNull
	private PatientEventType patientStatus;

}
