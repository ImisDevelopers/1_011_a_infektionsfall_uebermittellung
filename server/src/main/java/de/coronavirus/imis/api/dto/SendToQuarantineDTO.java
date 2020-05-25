package de.coronavirus.imis.api.dto;

import de.coronavirus.imis.domain.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendToQuarantineDTO {
	private List<String> patientIds;
	private LocalDate eventDate;
}
