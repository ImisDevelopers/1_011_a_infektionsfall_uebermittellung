package de.coronavirus.imis.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import de.coronavirus.imis.config.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
<<<<<<< HEAD
@EntityListeners(AuditingEntityListener.class) // for LastModifiedBy and LastModifiedDate
@Audited // For Envers Revisioning
=======
>>>>>>> parent of 8031129... envers working
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Incident {

	@Transient
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@Autowired
	private Logger logger;

	@Id
	private String id;

	//Col patient_id
	@ManyToOne
	private Patient patient;

	private String caseId;

	@Enumerated(EnumType.STRING)
	private EventType eventType;

	@Version
	private int version;

	@LastModifiedDate
	private LocalDateTime timestamp;

	// https://www.baeldung.com/database-auditing-jpa
	@ManyToOne
	@LastModifiedBy
	private User user;

}
