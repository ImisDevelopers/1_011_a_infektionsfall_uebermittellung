package de.coronavirus.imis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Audited
public class TestIncident extends Incident {

	private String testId;

	private TestType type;

	private TestStatus status;

	@ManyToOne
	@JsonIgnore
	@Audited(targetAuditMode = NOT_AUDITED)
	private Laboratory laboratory;

	private String comment;

	private byte[] report;

}