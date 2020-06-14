package de.coronavirus.imis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

import javax.persistence.*;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Audited
@Table(uniqueConstraints = {
	@UniqueConstraint(
		name = "LAB_UNIQUE_TEST_ID",
		columnNames = {"testId", "laboratory_id"}
	)
})
public class TestIncident extends Incident {

	private String testId;

	@Enumerated(EnumType.STRING)
	private TestType type;

	@Enumerated(EnumType.STRING)
	private TestStatus status;

	@ManyToOne
	@Audited(targetAuditMode = NOT_AUDITED)
	private Laboratory laboratory;

	private String comment;

	@Enumerated(EnumType.STRING)
	private TestMaterial material;

	private byte[] report;

}