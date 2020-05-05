package de.coronavirus.imis.domain;

import de.coronavirus.imis.config.domain.User;
import de.coronavirus.imis.services.RandomService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Audited
@MappedSuperclass
public abstract class Incident {

	protected Incident (IncidentType type)
	{
		id = type.toString() + "_" + UUID.randomUUID().toString().replace("-", "");
	}

	@Id
	private String id;

	@Audited(targetAuditMode = NOT_AUDITED)
	@ManyToOne
	private Patient patient;

	private String caseId;

	@Enumerated(EnumType.STRING)
	private EventType eventType;

	@LastModifiedDate
	private LocalDateTime versionTimestamp;

	@Audited(targetAuditMode = NOT_AUDITED)
	@ManyToOne
	@LastModifiedBy
	private User versionUser;
}
