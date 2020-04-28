package de.coronavirus.imis.api.dto;

import de.coronavirus.imis.domain.TestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class UpdateTestStatusDTO {
	private TestStatus status;
	private String comment;
	private String testId;
	private byte[] file;
}
