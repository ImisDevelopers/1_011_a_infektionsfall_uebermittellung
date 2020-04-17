package de.coronavirus.imis.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.coronavirus.imis.config.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class PatientEvent {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;

	@ManyToOne(fetch = FetchType.EAGER)
	private Patient patient;

	@ManyToOne()
	private User user;

	private OffsetDateTime eventTimestamp;
	private OffsetDateTime creationTimestamp;

	@Enumerated(EnumType.STRING)
	private PatientEventType patientEventType;

	private String comment;
}


