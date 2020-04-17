package de.coronavirus.imis.api.dto;

import de.coronavirus.imis.domain.LabTestEventType;
import lombok.Data;

@Data
public class UpdateLabTestDTO {
	private String id;
	private String laboratoryId;
	private String labInternalId;
	private LabTestEventType testStatus;
}
