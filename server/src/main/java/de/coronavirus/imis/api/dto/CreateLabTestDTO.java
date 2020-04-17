package de.coronavirus.imis.api.dto;

import de.coronavirus.imis.domain.TestType;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CreateLabTestDTO {
	private String patientCaseId;
	private String laboratoryId;
	private String testId;
	private String comment;
	private TestType testType;
	private OffsetDateTime eventTimestamp;
}
