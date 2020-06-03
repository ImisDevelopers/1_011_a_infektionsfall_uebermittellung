package de.coronavirus.imis.api.incidents;

/*

To ease migration, incidents are currently automatically created when
 - Creating Tests
 - Updating Tests
 - Sending to Quarantine
 - Creating Patients (equivalent to Initial Patient Event)
 - Scheduling appointments (implemented, not tested. Could not find frontend feature)

 */

import de.coronavirus.imis.domain.AdministrativeIncident;
import de.coronavirus.imis.domain.HospitalizationIncident;
import de.coronavirus.imis.domain.QuarantineIncident;
import de.coronavirus.imis.domain.TestIncident;
import de.coronavirus.imis.services.incidents.AdministrativeIncidentService;
import de.coronavirus.imis.services.incidents.HospitalizationIncidentService;
import de.coronavirus.imis.services.incidents.QuarantineIncidentService;
import de.coronavirus.imis.services.incidents.TestIncidentService;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/incidents")
public class IncidentController {

	private final TestIncidentService testIncidentService;
	private final QuarantineIncidentService quarantineIncidentService;
	private final AdministrativeIncidentService administrativeIncidentService;
	private final HospitalizationIncidentService hospitalizationIncidentService;

	@Data
	@Builder
	public static class PatientLogDto {
		@NotNull
		private final List<TestIncident> testIncidents;
		@NotNull
		private final List<QuarantineIncident> quarantineIncidents;
		@NotNull
		private final List<HospitalizationIncident> hospitalizationIncidents;
		@NotNull
		private final List<AdministrativeIncident> administrativeIncidents;
	}

	@GetMapping("/patient/{id}/log")
	public PatientLogDto getPatientLog(@PathVariable("id") String patientId) {
		return PatientLogDto.builder()
			.testIncidents(testIncidentService.getIncidentLogByPatientId(patientId))
			.quarantineIncidents(quarantineIncidentService.getIncidentLogByPatientId(patientId))
			.hospitalizationIncidents(hospitalizationIncidentService.getIncidentLogByPatientId(patientId))
			.administrativeIncidents(administrativeIncidentService.getIncidentLogByPatientId(patientId))
			.build();
	}

}
