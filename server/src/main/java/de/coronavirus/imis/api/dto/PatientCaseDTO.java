package de.coronavirus.imis.api.dto;

import de.coronavirus.imis.domain.Illness;
import de.coronavirus.imis.domain.LabTest;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

// TODO: Use PatientDTO instead of Patient in Controllers
@Data
@Accessors(chain = true)
public class PatientCaseDTO {
	private String id;
	private List<String> symptoms;
	private List<LabTest> labTests;
	private String speedOfSymptomsOutbreak;
	private Illness illness;
}
