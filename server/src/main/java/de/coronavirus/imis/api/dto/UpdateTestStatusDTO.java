package de.coronavirus.imis.api.dto;

import de.coronavirus.imis.domain.LabTestEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTestStatusDTO {
	private LabTestEventType status;
	private String comment;
	private String testId;
	private byte[] file;
}
