package de.coronavirus.imis.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.coronavirus.imis.config.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Audited
@MappedSuperclass
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "id")
public abstract class Incident {

  protected Incident() {
    id = UUID.randomUUID().toString().replace("-", "");
  }

  @Id
  private String id;

  @Audited(targetAuditMode = NOT_AUDITED)
  @ManyToOne
  private Patient patient;

  private String caseId;

  @NotNull
  @Enumerated(EnumType.STRING)
  private EventType eventType;

  // Date on which this Event was officially registered / mandated. Set by user.
  @NotNull
  private LocalDate eventDate;

  // Automated Timestamp
  @LastModifiedDate
  private LocalDateTime versionTimestamp;

  @Audited(targetAuditMode = NOT_AUDITED)
  @ManyToOne
  @LastModifiedBy
  private User versionUser;
}
