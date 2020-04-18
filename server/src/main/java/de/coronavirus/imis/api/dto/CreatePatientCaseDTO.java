package de.coronavirus.imis.api.dto;

import de.coronavirus.imis.domain.Illness;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CreatePatientCaseDTO {

	private String patientId;
	private List<String> symptoms;
	private String speedOfSymptomsOutbreak;
	private Illness illness;

}
