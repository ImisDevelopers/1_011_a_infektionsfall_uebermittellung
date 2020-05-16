package de.coronavirus.imis.api.dto;

import de.coronavirus.imis.domain.TestMaterial;
import de.coronavirus.imis.domain.TestType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateLabTestDTO {
	private String patientId;
	private String laboratoryId;
	private String testId;
	private String comment;
	private TestType testType;
	private TestMaterial testMaterial;
	private LocalDate eventDate;
}
