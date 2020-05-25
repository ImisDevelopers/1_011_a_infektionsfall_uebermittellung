package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.*;
import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientEvent;
import de.coronavirus.imis.services.IncidentService;
import de.coronavirus.imis.services.PatientEventService;
import de.coronavirus.imis.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

		if (dto.getDateOfHospitalization() != null)
			incidentService.addIncident(patient, dto.getDateOfHospitalization(), dto.getOnIntensiveCareUnit());

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
	public ResponseEntity<Patient> requestQuarantine(@PathVariable("id") String patientId, @RequestBody RequestQuarantineDTO statusDTO) {
		incidentService.addOrUpdateIncident(patientId, statusDTO);
		return ResponseEntity.ok(patientService.sendToQuarantine(patientId, statusDTO));
	}

	@PostMapping("/quarantine")
	@PreAuthorize("hasAnyRole('DEPARTMENT_OF_HEALTH')")
	public void sendToQuarantine(@RequestBody SendToQuarantineDTO dto) {
		dto.getPatientIds().forEach(patientId -> this.incidentService.updateQuarantineIncident(patientId, EventType.QUARANTINE_MANDATED, dto.getEventDate()));
	}

	@PostMapping("/event/order-test")
	@PreAuthorize("hasAnyRole('CLINIC', 'DEPARTMENT_OF_HEALTH', 'DOCTORS_OFFICE', 'TEST_SITE')")
	public ResponseEntity<PatientEvent> createOrderTestEvent(OrderTestEventDTO eventDTO) {
		var patient = patientService.findPatientById(eventDTO.getPatientId()).get();
		var event = eventService.createOrderTestEvent(patient);
		incidentService.addIncident(patient, Optional.empty(), EventType.ORDER_TEST, LocalDate.now());
		return ResponseEntity.ok(event);
	}
}
