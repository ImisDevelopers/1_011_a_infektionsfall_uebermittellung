package de.coronavirus.imis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Audited
public class AdministrativeIncident extends Incident {

	private Illness illness;

	private String comment;

	@Audited(targetAuditMode = NOT_AUDITED)
	@ManyToOne
	private Doctor responsibleDoctor;

	private LocalDate dateOfReporting;

}
