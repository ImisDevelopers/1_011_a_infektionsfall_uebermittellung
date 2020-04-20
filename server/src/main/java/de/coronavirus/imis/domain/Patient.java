package de.coronavirus.imis.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
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
	@ApiModelProperty(required = true)
	private String id;

	@OneToMany(mappedBy = "patient")
	@OrderBy("eventTimestamp asc")
	List<PatientEvent> events;

	@OneToMany(mappedBy = "patient")
	List<PatientInformation> information;

	@OneToMany(mappedBy = "patient")
	List<PatientCase> cases;

	@Enumerated(EnumType.STRING)
	@NotNull
	private PatientEventType patientStatus;

}
