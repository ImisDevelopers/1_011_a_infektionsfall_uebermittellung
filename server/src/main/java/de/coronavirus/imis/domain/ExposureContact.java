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
@Data
@Accessors(chain = true)
@DiscriminatorValue("person")
public class ExposureContact extends InfectionSource {
	@ManyToOne
	private Patient source;
	private String context;
}
