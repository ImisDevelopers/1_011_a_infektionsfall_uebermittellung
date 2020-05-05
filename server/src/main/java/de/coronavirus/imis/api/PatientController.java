package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.api.dto.OrderTestEventDTO;
import de.coronavirus.imis.api.dto.PatientSearchParamsDTO;
import de.coronavirus.imis.api.dto.PatientSimpleSearchParamsDTO;
import de.coronavirus.imis.api.dto.SendToQuarantineDTO;
import de.coronavirus.imis.domain.Patient;
<<<<<<< HEAD
import de.coronavirus.imis.services.IncidentService;
=======
import de.coronavirus.imis.domain.PatientEvent;
import de.coronavirus.imis.services.PatientEventService;
>>>>>>> dev
import de.coronavirus.imis.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

	private final PatientService patientService;
	private final IncidentService incidentService;
	private final PatientEventService eventService;

	@PostMapping
	public ResponseEntity<Patient> addPatient(@RequestBody CreatePatientDTO dto,
			@AuthenticationPrincipal Authentication auth) {
		boolean isAuthenticated = auth != null;
		var patient = patientService.addPatient(dto, isAuthenticated);

		if (patient == null) {
			return ResponseEntity.status(500).build();
		}
		return ResponseEntity.ok(patient);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Patient> getPatientForId(@PathVariable("id") String id) {
		return patientService.findPatientById(id).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping
	public Patient updatePatient(@RequestBody Patient patient) {
		return patientService.updatePatient(patient);
	}

	@GetMapping
	public ResponseEntity<List<Patient>> getAllPatients() {
		return ResponseEntity.ok(patientService.getAllPatients());
	}

	@PostMapping("/query-simple")
	@PreAuthorize("hasAnyRole('CLINIC', 'DOCTORS_OFFICE', 'TEST_SITE', 'DEPARTMENT_OF_HEALTH')")
	public List<Patient> queryPatientsSimple(@RequestBody PatientSimpleSearchParamsDTO query) {
		return patientService.queryPatientsSimple(query);
	}

	@GetMapping("/query-simple/count")
	@PreAuthorize("hasAnyRole('CLINIC', 'DEPARTMENT_OF_HEALTH', 'DOCTORS_OFFICE', 'TEST_SITE')")
	public Long countQueryPatientsSimple(@RequestParam String query) {
		return patientService.queryPatientsSimpleCount(query);
	}

	@PostMapping("/query")
	@PreAuthorize("hasAnyRole('CLINIC', 'DEPARTMENT_OF_HEALTH', 'DOCTORS_OFFICE', 'TEST_SITE')")
	public List<Patient> queryPatients(@RequestBody final PatientSearchParamsDTO patientSearchParamsDTO) {
		return patientService.queryPatients(patientSearchParamsDTO);
	}

	@PostMapping("/query/count")
	@PreAuthorize("hasAnyRole('CLINIC', 'DEPARTMENT_OF_HEALTH', 'DOCTORS_OFFICE', 'TEST_SITE')")
	public Long countQueryPatients(@RequestBody final PatientSearchParamsDTO patientSearchParamsDTO) {
		return patientService.countQueryPatients(patientSearchParamsDTO);
	}

	@PostMapping("/quarantine/{id}")
	@PreAuthorize("hasAnyRole('DEPARTMENT_OF_HEALTH')")
	public ResponseEntity<Patient> sendToQuarantine(@PathVariable("id") String patientId, @RequestBody SendToQuarantineDTO statusDTO) {
		incidentService.addOrUpdateIncident(patientId, statusDTO);
		return ResponseEntity.ok(patientService.sendToQuaratine(patientId, statusDTO));
	}

	@PostMapping("/event/order-test")
	@PreAuthorize("hasAnyRole('CLINIC', 'DEPARTMENT_OF_HEALTH', 'DOCTORS_OFFICE', 'TEST_SITE')")
	public ResponseEntity<PatientEvent> createOrderTestEvent(OrderTestEventDTO eventDTO) {
		var patient = patientService.findPatientById(eventDTO.getPatientId()).get();
		var event = eventService.createOrderTestEvent(patient);
		return ResponseEntity.ok(event);
	}
}
