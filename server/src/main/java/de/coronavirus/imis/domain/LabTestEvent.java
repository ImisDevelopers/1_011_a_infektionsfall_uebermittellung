package de.coronavirus.imis.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Data
public class LabTestEvent {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;

	@ManyToOne(fetch = FetchType.EAGER)
	private LabTest labTest;

	private OffsetDateTime eventTimestamp;

	@Enumerated(EnumType.STRING)
	private LabTestEventType labTestEventType;

	private String comment;
}
