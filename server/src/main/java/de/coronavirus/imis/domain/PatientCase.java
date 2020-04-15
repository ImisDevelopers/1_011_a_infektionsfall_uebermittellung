package de.coronavirus.imis.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class PatientCase {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;

	@Convert(converter = StringListConverter.class)
	private List<String> symptoms;

	@ManyToOne()
	private Patient patient;

	@OneToMany()
	private List<LabTest> labTests;
}
