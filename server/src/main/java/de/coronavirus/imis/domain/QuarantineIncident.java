package de.coronavirus.imis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@Accessors(chain = true)
@Audited
public class QuarantineIncident extends Incident {

	private LocalDate until;

	private String comment;

	public QuarantineIncident() {
		super(IncidentType.QUARANTINE);
	}

}