package de.coronavirus.imis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.List;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Audited
public class AdministrativeIncident extends Incident {

	/*
		Gathering all Case-Level Data.
		There is only one AdministrativeIncident per Case
	 */

	private Illness illness;

	private LocalDate dateOfIllness;

	private LocalDate dateOfReporting;

	private String comment;

	@Audited(targetAuditMode = NOT_AUDITED)
	@ManyToOne
	private Doctor responsibleDoctor;

	@Convert(converter = StringListConverter.class)
	private List<String> symptoms;

}
