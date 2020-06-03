package de.coronavirus.imis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@Accessors(chain = true)
@Audited
public class QuarantineIncident extends Incident {

	@NotNull
	private LocalDate until;

	private String comment;

}