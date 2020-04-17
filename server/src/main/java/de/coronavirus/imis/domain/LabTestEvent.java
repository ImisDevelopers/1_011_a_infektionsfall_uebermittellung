package de.coronavirus.imis.domain;

import de.coronavirus.imis.config.domain.User;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Data
@Accessors(chain = true)
public class LabTestEvent {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;

	@ManyToOne(fetch = FetchType.EAGER)
	private LabTest labTest;

	@ManyToOne
	private User user;

	// Timestamp when the event actually occured
	private OffsetDateTime eventTimestamp;
	// Timestamp when this event was created in the database
	private OffsetDateTime creationTimestamp;

	@Enumerated(EnumType.STRING)
	private LabTestEventType labTestEventType;

	private String comment;
}
