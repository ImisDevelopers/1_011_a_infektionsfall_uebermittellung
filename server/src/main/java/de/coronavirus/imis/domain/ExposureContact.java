package de.coronavirus.imis.domain;

import javax.persistence.*;
import java.time.LocalDate;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.coronavirus.imis.mapper.PatientMapper;
import de.coronavirus.imis.repositories.PatientRepository;

@Entity
@Component
@Data
@Accessors(chain = true)
public class ExposureContact {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private Patient source;
	@OneToOne
	private Patient contact;
	private String context; // e.g. contact in a medical facility
	private LocalDate dateOfContact;
	private String comment;
}
