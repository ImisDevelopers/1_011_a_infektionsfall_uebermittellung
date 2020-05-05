package de.coronavirus.imis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Audited
public class TestIncident extends Incident {

	private String testId;

	@Enumerated(EnumType.STRING)
	private TestType type;

	@Enumerated(EnumType.STRING)
	private TestStatus status;

	@ManyToOne
	@JsonIgnore
	@Audited(targetAuditMode = NOT_AUDITED)
	private Laboratory laboratory;

	private String comment;

	@Enumerated(EnumType.STRING)
	private TestMaterial testMaterial;

	private byte[] report;

	public TestIncident() {
		super(IncidentType.test);
	}
}