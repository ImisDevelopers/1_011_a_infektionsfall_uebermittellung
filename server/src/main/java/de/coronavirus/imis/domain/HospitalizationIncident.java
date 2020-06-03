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
public class HospitalizationIncident extends Incident {

	/*
		The hopsitalization date is captured on Incident level (EventDate), because:
		 - in the timeline, we want hospitalization to appear when it happened, not when it was entered into the system.
		 - this is consistent with quarantine
		 - the date of mandating hospitalization is usually the date when it actually happens
	 */
	private LocalDate releasedOn;

	private boolean intensiveCare;

}
