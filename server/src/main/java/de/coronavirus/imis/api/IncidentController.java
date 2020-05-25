package de.coronavirus.imis.api;

/*

To ease migration, incidents are currently automatically created when
 - Creating Tests
 - Updating Tests
 - Sending to Quarantine
 - Creating Patients (equivalent to Initial Patient Event)
 - Scheduling appointments (implemented, not tested. Could not find frontend feature)

 */

import de.coronavirus.imis.api.dto.ExposureContactDTO;
import de.coronavirus.imis.domain.Incident;
import de.coronavirus.imis.domain.IncidentType;
import de.coronavirus.imis.domain.QuarantineIncident;
import de.coronavirus.imis.services.IncidentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/incidents")
@AllArgsConstructor
public class IncidentController {

	private final IncidentService incidentService;

	@GetMapping("/{id}")
	public Incident getIncident(@PathVariable("id") String incidentId) {
		return incidentService.getCurrent(incidentId);
	}

	@GetMapping("/{id}/log")
	public List<Incident> getLog(@PathVariable("id") String incidentId) {
		return incidentService.getLog(incidentId, false);
	}

	@GetMapping("/patient/{id}")
	public List<Incident> getPatientCurrent(@PathVariable("id") String patientId) {
		return incidentService.getCurrentByPatient(patientId);
	}

	@GetMapping("/selected-for-quarantine")
	public List<QuarantineIncident> getSelectedForQuarantine() {
		return this.incidentService.getPatientsSelectedForQuarantine();
	}

	@GetMapping("/{type}/patient/{id}")
	public List<Incident> getPatientCurrentByType(@PathVariable("type") IncidentType incidentType, @PathVariable("id") String patientId) {
		return incidentService.getCurrentByPatient(patientId, incidentType);
	}

	@PostMapping("/{type}/patient")
	public Map<String, List<Incident>> getPatientsCurrentByType(@PathVariable("type") IncidentType incidentType, @RequestBody List<String> patientIds) {
		var result = new HashMap<String, List<Incident>>();
		for (String patientId : patientIds)
			result.put(patientId, getPatientCurrentByType(incidentType, patientId));
		return result;
	}

	@GetMapping("/patient/{id}/log")
	public List<Incident> getPatientLog(@PathVariable("id") String patientId) {
		return incidentService.getLog(patientId, true);
	}

	@GetMapping("/{type}/patient/{id}/log")
	public List<Incident> getPatientLogByType(@PathVariable("type") IncidentType incidentType, @PathVariable("id") String patientId) {
		return incidentService.getLog(incidentType.IMPLEMENTATION, patientId, true);
	}

}
