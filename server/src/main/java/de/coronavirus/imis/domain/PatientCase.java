package de.coronavirus.imis.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Accessors(chain = true)
public class PatientCase {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@ApiModelProperty(required = true)
	private String id;

	@Convert(converter = StringListConverter.class)
	private List<String> symptoms;

	@ManyToOne()
	private Patient patient;

	@OneToMany()
	private List<LabTest> labTests;

	private String speedOfSymptomsOutbreak;
	private Illness illness;
}
