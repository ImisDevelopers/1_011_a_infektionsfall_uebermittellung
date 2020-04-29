package de.coronavirus.imis.domain;

import de.coronavirus.imis.config.domain.User;
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

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class) // for LastModifiedBy and LastModifiedDate
@Audited // For Envers Revisioning
@MappedSuperclass
public abstract class Incident {

	@Transient
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@Autowired
	private Logger logger;

	@Id
	private String id;

	@Audited(targetAuditMode = NOT_AUDITED)
	@ManyToOne
	private Patient patient;

	private String caseId;

	@Enumerated(EnumType.STRING)
	private EventType eventType;

	@LastModifiedDate
	private LocalDateTime timestamp;

	@Audited(targetAuditMode = NOT_AUDITED)
	@ManyToOne
	@LastModifiedBy
	private User user;

}
