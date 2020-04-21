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
public class SendToQuarantineDTO {
	private String dateUntil;
	private String comment;
}
