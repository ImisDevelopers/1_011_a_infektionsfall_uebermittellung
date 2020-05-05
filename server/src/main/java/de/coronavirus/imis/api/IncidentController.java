package de.coronavirus.imis.api;

/*

To ease migration, incidents are currently automatically created when
 - Creating Tests
 - Updating Tests
 - Sending to Quarantine
 - Creating Patients (equivalent to Initial Patient Event)
 - Scheduling appointments (implemented, not tested. Could not find frontend feature)

 */

import de.coronavirus.imis.domain.Incident;
import de.coronavirus.imis.domain.IncidentType;
import de.coronavirus.imis.services.IncidentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
@AllArgsConstructor
public class IncidentController {

	private final IncidentService incidentService;

	@GetMapping("/{id}")
	public Incident getIncident(@PathVariable("id") String incidentId) {
		return incidentService.getCurrent(incidentId);
	}

	@GetMapping("/log/{id}")
	public List<Incident> getLog(@PathVariable("id") String incidentId) {
		return incidentService.getLog(incidentId, false);
	}

	@GetMapping("/patient/{id}")
	public List<Incident> getPatientCurrent(@PathVariable("id") String patientId) {
		return incidentService.getCurrentByPatient(patientId);
	}

	@GetMapping("/patient/{type}/{id}")
	public List<Incident> getPatientCurrentByType(@PathVariable("type") IncidentType incidentType, @PathVariable("id") String patientId) {
		return incidentService.getCurrentByPatient(patientId, incidentType);
	}

	@GetMapping("/patient/log/{id}")
	public List<Incident> getPatientLog(@PathVariable("id") String patientId) {
		return incidentService.getLog(patientId, true);
	}

	@GetMapping("/patient/log/{type}/{id}")
	public List<Incident> getPatientLogByType(@PathVariable("type") IncidentType incidentType, @PathVariable("id") String patientId) {
		return incidentService.getLog(incidentType.IMPLEMENTATION, patientId, true);
	}

}
